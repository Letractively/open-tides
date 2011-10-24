/*
   Licensed to the Apache Software Foundation (ASF) under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ASF licenses this file
   to you under the Apache License, Version 2.0 (the
   "License"); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.    
 */

package org.opentides.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.opentides.ControllerException;
import org.opentides.InvalidImplementationException;
import org.opentides.bean.BaseEntity;
import org.opentides.bean.FileInfo;
import org.opentides.bean.SearchResults;
import org.opentides.bean.SystemCodes;
import org.opentides.bean.Uploadable;
import org.opentides.bean.UserDefinable;
import org.opentides.service.BaseCrudService;
import org.opentides.service.FileInfoService;
import org.opentides.service.UserDefinedFieldService;
import org.opentides.util.DateUtil;
import org.opentides.util.FileUtil;
import org.opentides.util.StringUtil;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractFormController;

/**
 * This controller is used to handle basic CRUD operations.
 * Operation is identified using URL param "action".
 * If action is null, page defaults to search all.
 * 
 * @author allanctan
 */
@SuppressWarnings("deprecation")
public class BaseCrudController<T extends BaseEntity> extends
        AbstractFormController {

    private static Logger _log = Logger.getLogger(BaseCrudController.class);

    public static final String ACTION = "action";
    public static final String CREATE = "create";
    public static final String READ = "read";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String SEARCH = "search";
    public static final String ID = "codeId";

    private BaseCrudService<T> service;
    private BaseCrudService<SystemCodes> systemCodesService;
	private BaseCrudService<FileInfo> fileInfoService;
	private UserDefinedFieldService userDefinedFieldService;
    private String uploadPath = File.separator + "uploads";
    private boolean requireUpload = false;
    private boolean multipleUpload = true;
    private boolean supportsPaging = true;
    private boolean skipAction = false;
    private boolean exactMatch = false;
    private int pageSize;
    private int numLinks;

    /**
     * Inject view for displaying search page
     */
    private String searchView;
    /**
     * Inject view for refresh entry on search page
     */
    private String refreshView;
    /**
     * Inject view for form page.
     */
    private String formView;
    /**
     * Internal use. Don't mess with me.
     */
    private String showView;

    /**
     * When user requests for READ or UPDATE action, the corresponding object
     * needs to be loaded for display. This implementation invokes {@link
     * BaseCrudService.load(HttpServletRequest)} to retrieve the object for
     * loading.
     * 
     * @see BaseCrudService.load
     */
    @Override
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        String action = getAction(request);
        if (READ.equals(action) || UPDATE.equals(action)) {
            return service.load(request.getParameter(ID));
        }
        return super.formBackingObject(request);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.mvc.BaseCommandController#suppressValidation
     * (javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected boolean suppressValidation(HttpServletRequest request) {
        String action = getAction(request);
        if (SEARCH.equals(action) || DELETE.equals(action))
            return true;
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.web.servlet.mvc.AbstractFormController#isFormSubmission
     * (javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected boolean isFormSubmission(HttpServletRequest request) {
        if ("POST".equals(request.getMethod()))
            return true;
        String action = getAction(request);
        if (SEARCH.equals(action) || DELETE.equals(action))
            return true;
        // make sure showView is set in case it is not form Submission
        if (CREATE.equals(action) || UPDATE.equals(action))
            showView = getFormView();
        else if (READ.equals(action)) {
            showView = getRefreshView();
        }
        return false;
    }

    /**
     * This implementation shows the configured form view, delegating to the
     * analogous
     * {@link #showForm(HttpServletRequest, HttpServletResponse, BindException, Map)}
     * variant with a "controlModel" argument.
     * <p>
     * Can be called within
     * {@link #onSubmit(HttpServletRequest, HttpServletResponse, Object, BindException)}
     * implementations, to redirect back to the form in case of custom
     * validation errors (errors not determined by the validator).
     * <p>
     * Can be overridden in subclasses to show a custom view, writing directly
     * to the response or preparing the response before rendering a view.
     * <p>
     * If calling showForm with a custom control model in subclasses, it's
     * preferable to override the analogous showForm version with a controlModel
     * argument (which will handle both standard form showing and custom form
     * showing then).
     * 
     * @see #setFormView
     * @see #showForm(HttpServletRequest, HttpServletResponse, BindException,
     *      Map)
     */
    @Override
    protected ModelAndView showForm(HttpServletRequest request,
            HttpServletResponse response, BindException errors)
            throws Exception {
        return showForm(request, response, errors, null);
    }

    /**
     * This implementation shows the configured form view.
     * <p>
     * Can be called within
     * {@link #onSubmit(HttpServletRequest, HttpServletResponse, Object, BindException)}
     * implementations, to redirect back to the form in case of custom
     * validation errors (errors not determined by the validator).
     * <p>
     * Can be overridden in subclasses to show a custom view, writing directly
     * to the response or preparing the response before rendering a view.
     * 
     * @param request
     *            current HTTP request
     * @param errors
     *            validation errors holder
     * @param controlModel
     *            model map containing controller-specific control data (e.g.
     *            current page in wizard-style controllers or special error
     *            message)
     * @return the prepared form view
     * @throws Exception
     *             in case of invalid state or arguments
     * @see #setFormView
     */
    @SuppressWarnings("rawtypes")
	protected ModelAndView showForm(HttpServletRequest request,
            HttpServletResponse response, BindException errors, Map controlModel)
            throws Exception {
        return showForm(request, errors, getShowView(), controlModel);
    }

    /**
     * Create a reference data map for the given request and command, consisting
     * of bean name/bean instance pairs as expected by ModelAndView.
     * <p>
     * The default implementation delegates to
     * {@link #referenceData(HttpServletRequest)}. Subclasses can override this
     * to set reference data used in the view.
     * 
     * @param request
     *            current HTTP request
     * @param command
     *            form object with request parameters bound onto it
     * @param errors
     *            validation errors holder
     * @return a Map with reference data entries, or <code>null</code> if none
     * @throws Exception
     *             in case of invalid state or arguments
     * @see ModelAndView
     */
    @SuppressWarnings("rawtypes")
	protected Map referenceData(HttpServletRequest request, Object command,
            Errors errors) throws Exception {
        return referenceData(request);
    }

    /**
     * Create a reference data map for the given request. Called by the
     * {@link #referenceData(HttpServletRequest, Object, Errors)} variant with
     * all parameters.
     * <p>
     * The default implementation returns <code>null</code>. Subclasses can
     * override this to set reference data used in the view.
     * 
     * @param request
     *            current HTTP request
     * @return a Map with reference data entries, or <code>null</code> if none
     * @throws Exception
     *             in case of invalid state or arguments
     * @see #referenceData(HttpServletRequest, Object, Errors)
     * @see ModelAndView
     */
    @SuppressWarnings("rawtypes")
	protected Map referenceData(HttpServletRequest request) throws Exception {
        return null;
    }

    /**
     * This implementation performs the corresponding action for CRUD operation.
     * Operations include CREATE, UPDATE, DELETE and SEARCH. To override the
     * operations, you may override the corresponding action methods.
     * 
     * @see #processFormSubmission(HttpServletRequest, HttpServletResponse,
     *      Object, BindException)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected ModelAndView processFormSubmission(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        // check for bind errors - extracted from SimpleFormController
        if (errors.hasErrors()) {
            if (_log.isDebugEnabled()) {
                _log.debug("Data binding errors: " + errors.getErrorCount());
                _log.debug(errors.toString());
            }
            return showForm(request, response, errors);
        }

        try {
            processUpload(request, response, command, errors, "attachment");

            String action = getAction(request);
            setSkipAction(false);
            T obj = (T) command;
            SearchResults results = new SearchResults<T>(pageSize, numLinks);

            // perform the action
            if (action.equals(UPDATE)) {
                showView = getRefreshView();
                this.preUpdateAction(request, response, obj, errors);
                if (!skipAction) {
                    service.save(obj);
                }
                this.postUpdateAction(request, response, obj, errors);
            } else if (action.equals(CREATE)) {
                showView = getRefreshView();
                this.preCreateAction(request, response, obj, errors);
                if (!skipAction) {
                    service.save(obj);
                }
                this.postCreateAction(request, response, obj, errors);
            } else if (action.equals(DELETE)) {
                showView = getRefreshView();
                this.preDeleteAction(request, response, errors, request
                        .getParameter(ID));
                if (!skipAction) {
                    service.delete(request.getParameter(ID));
                }
                this.postDeleteAction(request, response, errors, request
                        .getParameter(ID));
            } else if (action.equals(SEARCH)) {
                showView = getSearchView();
                this.preSearchAction(request, response, obj, errors);
                if (!skipAction) {
                    long startTime = System.currentTimeMillis();
                    String strPage = request.getParameter("page");
                    int currPage = StringUtil.convertToInt(strPage, 1);
                    results.setCurrPage(currPage);
                    results.setTotalResults(this.countAction(obj));
                    int start = results.getStartIndex();
                    int total = results.getPageSize();
                    if (supportsPaging) {
                        if (command == null) {
                            // no command, let's search everything
                            results.addResults(service.findAll(start, total));
                        } else {
                            // let's do a query by example
                            results.addResults(service.findByExample(obj,
                                    exactMatch, start, total));
                        }
                    } else {
                        if (command == null) {
                            // no command, let's search everything
                            results.addResults(service.findAll());
                        } else {
                            // let's do a query by example
                            results.addResults(service.findByExample(obj,
                                    exactMatch));
                        }
                    }
                    results.setSearchTime(System.currentTimeMillis()
                            - startTime);
                }
                results = this.postSearchAction(request, response, obj, errors,
                        results);
            }

            // return the response and view
            Map<String, Object> model = referenceData(request, command, errors);
            if (model == null)
                model = new HashMap<String, Object>();
            if (UserDefinable.class.isAssignableFrom(command.getClass()))
            	model.put("dropList",userDefinedFieldService.getDropDownReferenceData(command.getClass().getSimpleName()));
            model.put(getCommandName(), obj);
            model.put("results", results);
            model.putAll(errors.getModel());
            if (action.equals(CREATE))
                model.put("newRow", true);
            return new ModelAndView(getShowView(), model);
        } catch (ControllerException ce) {
            // check for bind errors - most likely due to no file uploaded
            if (errors.hasErrors()) {
                if (_log.isDebugEnabled()) {
                    _log
                            .debug("Data binding errors: "
                                    + errors.getErrorCount());
                    _log.debug(errors.toString());
                }
                return showForm(request, response, errors);
            } else {
                String message = "ControllerException thrown but no errors reported.";
                _log.error(message, ce);
                throw new InvalidImplementationException(message, ce);
            }
        }
    }

    /**
     * 
     * This is an internal helper that process file upload if supported. File
     * upload must use htmlId to refer html upload element.
     * 
     * @param request
     * @param response
     * @param command
     * @param errors
     * @param htmlId
     * @throws IOException
     */
    protected FileInfo processUpload(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors,
            String htmlId) throws IOException {
		FileInfoService fileInfoService = (FileInfoService) this.fileInfoService;
		
        // are we expecting file upload?
        if (Uploadable.class.isAssignableFrom(command.getClass())
                && request instanceof MultipartHttpServletRequest) {
            Uploadable upload = (Uploadable) command;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile = multipartRequest.getFile(htmlId);

            // check if there is anything to process
            if (multipartFile != null && !multipartFile.isEmpty()) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFilename(multipartFile.getOriginalFilename());
                fileInfo.setFileSize(multipartFile.getSize());
                fileInfo.setFullPath(uploadPath);

                try {
                    // setup the directory
                    File directory;
                    directory = FileUtil.createDirectory(uploadPath);
                    String subdir = directory.getAbsoluteFile()
                            + File.separator
                            + DateUtil.convertShortDate(new Date());
                    File subDirectory = FileUtil.createDirectory(subdir);
					
					String filePath = subDirectory.getAbsoluteFile() + File.separator + multipartFile.getOriginalFilename();

					// change filename if already existing in the given path to avoid overriding of files
					if(fileInfoService.getFileInfoByFullPath(filePath) != null){
						Long fileCnt = 1L;
						String newFilePath = subDirectory.getAbsoluteFile() + File.separator + fileCnt.toString() + "_" + multipartFile.getOriginalFilename() ;
						while (fileInfoService.getFileInfoByFullPath(newFilePath)!=null) {
							fileCnt++;
							newFilePath = subDirectory.getAbsoluteFile() + File.separator + fileCnt.toString() + "_" +  multipartFile.getOriginalFilename();
						} 		

						filePath = newFilePath;
						fileInfo.setOriginalFileName(multipartFile.getOriginalFilename());
					}
					
					File uploadFile = new File(filePath);
					
                    // update file path information
                    fileInfo.setFullPath(uploadFile.getAbsolutePath());
                    _log.debug("Uploading file to " + fileInfo.getFullPath());
                    // now copy the file
                    FileUtil.copyMultipartFile(multipartFile, uploadFile);
                    // add fileinfo
                    List<FileInfo> files = null;
                    if (multipleUpload)
                        files = upload.getFiles();
                    if (files == null)
                        files = new ArrayList<FileInfo>();
                    files.add(fileInfo);
                    upload.setFiles(files);
                    return fileInfo;
                } catch (IOException e) {
                    _log.error("Failed to upload file.", e);
                    throw e;
                }
            } else {
                if (isRequireUpload()) {
                    errors.reject(htmlId, null, "No uploaded file selected.");
                    throw new ControllerException("No uploaded file selected.");
                }
            }
        }
        return null;
    }

    /**
     * This is an internal helper that retrieves the proper ACTION based on
     * request parameters. When no action is found, defaults to SEARCH.
     * 
     * @param request
     * @return action to perform
     */
    protected String getAction(HttpServletRequest request) {
        String action = request.getParameter("action");
        // default to SEARCH
        if (StringUtil.isEmpty(action)) {
            action = SEARCH;
        }
        return action;
    }

    /**
     * Override this method to perform pre-processing on new data being saved.
     * To terminate processing, call setSkipAction with parameter true.
     * 
     * @param command
     */

    protected void preCreateAction(HttpServletRequest request,
            HttpServletResponse response, T command, BindException errors) {
        preCreateAction(command);
    }

    protected void preCreateAction(T command) {
    }

    /**
     * Override this method to perform post-processing on new data being saved.
     * 
     * @param command
     */

    protected void postCreateAction(HttpServletRequest request,
            HttpServletResponse response, T command, BindException errors) {
        postCreateAction(command);
    }

    protected void postCreateAction(T command) {
    }

    /**
     * Override this method to perform pre-processing on data being updated. To
     * terminate processing, call setSkipAction with parameter true.
     * 
     * @param command
     */

    protected void preUpdateAction(HttpServletRequest request,
            HttpServletResponse response, T command, BindException errors) {
        preUpdateAction(command);
    }

    protected void preUpdateAction(T command) {
    }

    /**
     * Override this method to perform post-processing on data being updated.
     * 
     * @param command
     */

    protected void postUpdateAction(HttpServletRequest request,
            HttpServletResponse response, T command, BindException errors) {
        postUpdateAction(command);
    }

    protected void postUpdateAction(T command) {
    }

    /**
     * Override this method to perform pre-processing on data being deleted. To
     * terminate processing, call setSkipAction with parameter true.
     * 
     * @param command
     */

    protected void preDeleteAction(HttpServletRequest request,
            HttpServletResponse response, BindException errors, String id) {
        preDeleteAction(id);
    }

    protected void preDeleteAction(String id) {
    }

    /**
     * Override this method to perform post-processing on data being deleted.
     * 
     * @param command
     */

    protected void postDeleteAction(HttpServletRequest request,
            HttpServletResponse response, BindException errors, String id) {
        postDeleteAction(id);
    }

    protected void postDeleteAction(String id) {
    }

    /**
     * Override this method to perform pre-processing on data search. To
     * terminate processing, call setSkipAction with parameter true.
     * 
     * @param command
     *            criteria used for search
     */

    protected void preSearchAction(HttpServletRequest request,
            HttpServletResponse response, T command, BindException errors) {
        preSearchAction(command);
    }

    protected void preSearchAction(T command) {
    }

    /**
     * Override this method to perform post-processing on data search.
     * 
     * @param command
     */

    protected SearchResults<T> postSearchAction(HttpServletRequest request,
            HttpServletResponse response, T command, BindException errors,
            SearchResults<T> result) {
        return postSearchAction(result);
    }

    protected SearchResults<T> postSearchAction(SearchResults<T> result) {
        return result;
    }

    /**
     * Override this method if you want to control how data will be searched
     * along with searchAction(). This method returns the total result count.
     * 
     * @param command
     * @return
     */
    protected long countAction(T command) {
        if (command == null) {
            // no command, let's search everything
            return service.countAll();
        } else {
            return service.countByExample(command, exactMatch);
        }
    }

    /**
     * Retrieves all registered system codes for a category.
     * 
     * @param String
     *            categoryName
     * @return
     */
    protected final List<SystemCodes> getSystemCodesByCategory(
            String categoryName) {
        SystemCodes example = new SystemCodes();
        example.setCategory(categoryName);
        return systemCodesService.findByExample(example, true);
    }

    /**
     * @param service
     *            the service to set
     */
    public final void setService(BaseCrudService<T> service) {
        this.service = service;
    }

    /**
     * @return the searchView
     */
    public final String getSearchView() {
        return searchView;
    }

    /**
     * @param searchView
     *            the searchView to set
     */
    public final void setSearchView(String searchView) {
        this.searchView = searchView;
    }

    /**
     * @return the refreshView
     */
    public final String getRefreshView() {
        return refreshView;
    }

    /**
     * @param refreshView
     *            the refreshView to set
     */
    public final void setRefreshView(String refreshView) {
        this.refreshView = refreshView;
    }

    /**
     * @return the formView
     */
    public final String getFormView() {
        return formView;
    }

    /**
     * @param formView
     *            the formView to set
     */
    public final void setFormView(String formView) {
        this.formView = formView;
    }

    /**
     * @return the showView
     */
    public final String getShowView() {
        return showView;
    }

    /**
     * @param showView
     *            the showView to set
     */
    protected final void setShowView(String showView) {
        this.showView = showView;
    }

    /**
     * @return the service
     */
    protected final BaseCrudService<T> getService() {
        return service;
    }

    /**
     * @return the pageSize
     */
    public final int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public final void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the numLinks
     */
    public final int getNumLinks() {
        return numLinks;
    }

    /**
     * @param numLinks
     *            the numLinks to set
     */
    public final void setNumLinks(int numLinks) {
        this.numLinks = numLinks;
    }

    /**
     * @param systemCodesService
     *            the systemCodesService to set
     */
    public final void setSystemCodesService(
            BaseCrudService<SystemCodes> systemCodesService) {
        this.systemCodesService = systemCodesService;
    }

    /**
     * @return the systemCodesService
     */
    public final BaseCrudService<SystemCodes> getSystemCodesService() {
        return systemCodesService;
    }

    /**
	 * @param userDefinedFieldService the userDefinedFieldService to set
	 */
	public final void setUserDefinedFieldService(
			UserDefinedFieldService userDefinedFieldService) {
		this.userDefinedFieldService = userDefinedFieldService;
	}

	/**
     * @return the supportsPaging
     */
    public final boolean isSupportsPaging() {
        return supportsPaging;
    }

    /**
     * @param supportsPaging
     *            the supportsPaging to set
     */
    public final void setSupportsPaging(boolean supportsPaging) {
        this.supportsPaging = supportsPaging;
    }

    /**
     * @param skipAction
     *            the skipAction to set
     */
    protected void setSkipAction(boolean skipAction) {
        this.skipAction = skipAction;
    }

    /**
     * @return the uploadPath
     */
    public final String getUploadPath() {
        return uploadPath;
    }

    /**
     * @param uploadPath
     *            the uploadPath to set
     */
    public final void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    /**
     * @return the requireUpload
     */
    public final boolean isRequireUpload() {
        return requireUpload;
    }

    /**
     * @param requireUpload
     *            the requireUpload to set
     */
    public final void setRequireUpload(boolean requireUpload) {
        this.requireUpload = requireUpload;
    }

    /**
     * @return the multipleUpload
     */
    public boolean isMultipleUpload() {
        return multipleUpload;
    }

    /**
     * @param multipleUpload
     *            the multipleUpload to set
     */
    public void setMultipleUpload(boolean multipleUpload) {
        this.multipleUpload = multipleUpload;
    }

    /**
     * @return the exactMatch
     */
    public final boolean isExactMatch() {
        return exactMatch;
    }

    /**
     * @param exactMatch
     *            the exactMatch to set
     */
    public final void setExactMatch(boolean exactMatch) {
        this.exactMatch = exactMatch;
    }

	public void setFileInfoService(BaseCrudService<FileInfo> fileInfoService) {
		this.fileInfoService = fileInfoService;
	}

}
