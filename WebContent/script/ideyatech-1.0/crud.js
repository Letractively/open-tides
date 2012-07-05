/* 
Copyright (c) 2011, Ideyatech, Inc. All rights reserved.
Code licensed under the BSD License
 */

/**
 * The global namespace.
 */
var IDEYATECH = {};

/**
 * for our widget class
 */
IDEYATECH.widgets = {};

/**
 * Safe javascript logging to Firebug. Copied from:
 * http://www.mario.net.au/blog/better-firebug-and-jquery-logging.html
 * 
 */
jQuery.fn.log = function() {
	if (window.console && window.console.log) {
		if (arguments.length) {
			console.log("%o args: %o", this, arguments);
		} else {
			console.log(this);
		}
	}
	return this;
};

/**
 * For adding inline labels on text boxes
 */
jQuery.fn.watermark = function(label) {
    if (IDEYATECH.util.isEmpty(this.val())) {
        this.val(label);
    } 
    this.blur(function() {
        if (IDEYATECH.util.isEmpty($(this).val())) {
            $(this).val(label);
        } 
    });
    this.focus(function() {
       if ($(this).val() == label) {
           $(this).val('');
       } 
    });    
};

/**
 * For removing watermark, called before submission of form.
 */
jQuery.fn.cleanmark = function(label) {
    if (this.val() == label) {
        this.val('');
    }
};

/**
 * Provides common utility functions.
 * 
 * @class util
 * @namespace IDEYATECH
 */

IDEYATECH.util = function() {

	/**
	 * Private method that replaces response text. This is generally used for
	 * multipart form.
	 * 
	 * @param str -
	 *            string response Text
	 * @return newResponseText string
	 */
	var __hackResponse = function(str) {
		/*
		 * hack tags, we use [] instead of <> because of its limitation (the
		 * response text automatically append a closing tag when seeing a tag
		 * inside it
		 */
		str = str.replace(/\[multipart_tr(.*?)\]/gi, "<tr$1>");
		str = str.replace(/\[\/multipart_tr\]/gi, "</tr>");
		str = str.replace(/\[multipart_td(.*?)\]/gi, "<td$1>");
		str = str.replace(/\[\/multipart_td\]/gi, "</td>");
		/* you may need to add another tag replacement here */
		return str;
	};

	/**
	 * Private helper to replace the div with the HTML response args[0] {String}
	 * element to replace args[1] {function} callback function args[2] {boolean}
	 * should javascript be executed
	 */
	var __replace = function(response, args) {
		/*
		 * Notice that when using yConnect.setForm(formObject, true); the
		 * responseText returned by a callback strips improper tags (e.g) <tr><td>Hello</td></tr>
		 * will become Hello This method will hack the response text, replacing
		 * your customize tags with proper HTML tags Prerequisite: you need to
		 * map your customize tags to our str replace... {o} object response
		 */
		var str = __hackResponse(response);
		var userCallback = args[1];
		IDEYATECH.util.updateInnerHTML(args[0], str);
		if (typeof userCallback == 'function') {
			try {
				userCallback.call();
			} catch (err) {
			}
		}
		if (args[2]) 
		  	__evaluateJS(str);
		
	};

	/**
	 * Private helper to append the response as sibling of the div o.argument[0]
	 * {String} element to append o.argument[1] {function} callback function
	 * o.argument[2] {boolean} should javascript be executed
	 */
	var __append = function(response, args) {
		var str = __hackResponse(response);

		var rootId = __getFirstElementId(str, "tr");
        
		if (IDEYATECH.util.isEmpty(rootId)) {
            //   if no tr with that id, try div
            rootId = __getFirstElementId(str, "div");
        }
        
		if (!IDEYATECH.util.isEmpty(rootId) && $('#' + rootId).length == 0) {
			var parent = document.getElementById(args[0]);
			var userCallback = args[1];
			if (parent != null) 
				IDEYATECH.util.updateInnerHTML(parent.id, parent.innerHTML + str);
			if (typeof userCallback == 'function') {
				try {
					userCallback.call();
				} catch (err) {
				}
			}
			if (args[2]) 
	  			__evaluateJS(response);
		} else {
			if (!IDEYATECH.util.isEmpty(rootId))
				args[0] = rootId;
			__replace(response, args);
		}
	};

	/**
	 * Private helper to retrieve the first element id in the string
	 */
	var __getFirstElementId = function(str, tag) {
		var matchExp = new RegExp('<' + tag + '[^>]+id="(.\*?)"[^>]*>', 'img');
		var matches = matchExp.exec(str) || [];
		return matches[1];
	};

	/**
	 * Private helper that will evaluate the ajax response
	 */
	var __evaluateJS = function(response) {
		var script = IDEYATECH.util.extractScripts(response);
		eval(script || '');
	};

	var __failure = function(o) {
		IDEYATECH.util.log('Connection failed :: ' + o.statusText);
	};

	return {

		/**
		 * Failsafe logging of messages to firebug console.
		 * @message - message to log
		 */
		log : function(message) {
		    if (window.console && window.console.log) {
		        console.log(message);
		    }
		},
		
		/**
		 * Work around for innerHTML - replace value of an element
		 * 
		 * @id - id of the element
		 * @replacement - new value that will be placed in the element of the
		 *              given id
		 */
		updateInnerHTML : function(id, replacement) {
			// TODO: Verify if this works in IE when replacing tr element
			$('#' + id).html(replacement);
		},

		/**
		 * Checks if a given variable is empty
		 * 
		 * @method isEmpty
		 * @param {object}
		 *            variable to check
		 * 
		 */
		isEmpty : function(obj) {
			if (obj == null || typeof (obj) == "undefined" || obj.length == 0
					|| obj == "") {
				return true;
			} else
				return false;
		},
		/**
		 * Any spaces entered by the user will be removed
		 * 
		 * @method removeSpaces
		 * @param str -
		 *            string to evaluate
		 */
		removeSpaces : function(str) {
			return str.split(' ').join('');
		},
		/**
		 * Extracts the script from a set of result
		 * 
		 * @method extractScripts
		 * @param {String}
		 *            string to extract for script
		 */
		extractScripts : function(result) {
			var matchAll = new RegExp('<script[^>]*>([\\S\\s]*?)<\/script>',
					'img');
			var stripAll = new RegExp('<\/?script[^>]*>', 'img');
			var scripts = result.match(matchAll) || [];
			var script = "";
			for (i = 0; i < scripts.length; i++) {
				script += scripts[i].replace(stripAll, '');
			}
			return script;
		},
		/**
		 * Loads update page into the given div and parameters.
		 * 
		 * @method loadPage
		 * @param {String}
		 *            url URL where request will be sent
		 * @param {Object}
		 *            argument - can include any of the following 
		 *            - divId = element id containing the row to insert/update page 
		 *            - callback = method to execute after loading 
		 *            - append = true, will append result instead of replace
		 */
		loadPage : function(url, argument) {
			var args = [ argument['divId'], argument['callback'] ];
			var successCallback = function(data) {
				__replace(data, args);
			};

			if (argument['append'] == 'true')
				successCallback = function(data) {
					__append(data, args);
				};

			$.ajax(url, {
				success : successCallback,
				error : __failure,
				type: "GET",
				dataType: "html"
			});
		},
		
		/**
		 * Loads update page into the given div and parameters.
		 * 
		 * @method loadPage
		 * @param {String}
		 *            url URL where request will be sent
		 * @param {Object}
		 *            argument - can include any of the following 
		 *            - divId = element id containing the row to insert/update page 
		 *            - callback = method to execute after loading 
		 *            - append = true, will append result instead of replace
		 *            - cache = true, will allow browser caching
		 */
		loadPagewithCache : function(url, argument) {
			var args = [ argument['divId'], argument['callback'] ];
			var successCallback = function(data) {
				__replace(data, args);
			};

			if (argument['append'] == 'true')
				successCallback = function(data) {
					__append(data, args);
				};

			$.ajax(url, {
				success : successCallback,
				error : __failure,
				type: "GET",
			    dataType: "html",
				cache:true
			});
		},


		/**
		 * Submits the form for processing.
		 * 
		 * @method submitForm
		 * @param {String}
		 *            url URL where request will be sent
		 * @param {Object}
		 *            argument - can include any of the following 
		 *            - divId = element id containing the row to insert/update page 
		 *            - callback = method to execute after loading 
		 *            - append = true, will append result instead of replace
		 * 			  - postdata = parameter to append in the form submission 
		 * 			  - formName = name of the form to be submitted
		 * 			  - evaluate = true, will evaluate javascript code
		 */

		submitForm : function(url, argument) {
			var args = [ argument['divId'], argument['callback'], argument['evaluate'] ];

			var successCallback = function(data) {
				__replace(data, args);
			};
			if (argument['append'] == 'true')
				successCallback = function(data) {
					__append(data, args);
				};
				
			var options = {
					type : 'POST',
					success : successCallback,
					error : __failure,
					data : argument['postdata']			                
				};
			try {
				// use jquery.form is available - this is needed to support multipart
				$('#' + argument['formName']).ajaxSubmit(options);				
			} catch (err) {
				// use stardard ajax submit
				if (argument['multipart'] == 'true' ||
					$('#' + argument['formName']).attr('enctype') == 'multipart/form-data')
					alert('Program Error.\n Please include jquery.form.js to support multipart form submission.' + err);
				options['data'] = $('#' + argument['formName']).serialize() + '&' + argument['postdata'];
				$.ajax(url, options);				
			};
		}
	};
}();

/**
 * Provides ajax based CRUD functions.
 * 
 * @class crud
 * @namespace IDEYATECH
 */
IDEYATECH.crud = function() {

	var __failure = function(o) {
		IDEYATECH.util.log('Connection failed :: ' + o.statusText);
	};

	return {
		/**
		 * Applies alternating color on the result table.
		 * 
		 * @param {Object}
		 *            argument - - stylePrefix = prefix containing the style
		 *            class. Defaults to "row-style". Appends -1 or -0 on prefix
		 *            for alternating styles.
		 */
		refreshTable : function(divId, argument) {
			var prefix = argument['stylePrefix'];
			if (prefix === undefined) {
				prefix = 'row-style-';
			}
			$("#" + divId + " tr").removeClass(prefix + '1 ' + prefix + '0');
			$("#" + divId + " tr:odd").addClass(prefix + '0');
			$("#" + divId + " tr:even").addClass(prefix + '1');
		},
		/**
		 * Prompts for deletion and delete if confirmed.
		 * 
		 * @method confirmDelete
		 * @param {String}
		 *            id id of record to delete
		 * @return {String} title Main title of record to display.
		 */
		confirmDelete : function(divId, url, title) {
			if (confirm('Are you sure you want to delete [' + title + ']?')) {
				var args = [ divId ];
				var successCallback = function(data) {
					$('#' + args[0]).fadeOut('slow');
				};
				var errorCallback = function(data) {
					if(data.responseText.indexOf("HTTP Status 500")> -1){
						alert("You're trying to delete [" + title + "], but is being used by the system.");
					}else{
						__failure;
					}
				};
				$.ajax(url, {
					type : 'GET',
					success : successCallback,
					error : errorCallback
				});
			}
		},
		/**
		 * Replace the new form displayed with add new button
		 * 
		 * @method cancelNew
		 */
		cancelNew : function(prefix) {
			IDEYATECH.util.updateInnerHTML(prefix + 'row-new', "");
		},
		/**
		 * Loads the add record form
		 * 
		 * @method addRecord
		 * @param {String}
		 *            prefix of element for add form, appended to '-row-new'
		 * @param {String}
		 *            url of the form
		 * @param {String}
		 * 			  optional - callback function upon successful transaction
		 */
		addRecord : function(prefix, url) {
			var args = {
				divId : prefix + 'row-new',
				evaluate: true
			};
			if (typeof addCallback ==  'function') {
				args['callback'] = addCallback;
			}
			IDEYATECH.util.loadPage(url, args);
		},
		/**
		 * Loads the edit record form
		 * 
		 * @method editRecord
		 * @param {String}
		 *            prefix of element for add form, appended to '-row-new'
		 * @param {String}
		 *            url of the form
		 * @param {String}
		 * 			  optional - callback function upon successful transaction
		 */
		editRecord : function(prefix, url, editCallback) {
			var args = {
				divId : prefix,
				evaluate: true
			};
			if (typeof editCallback ==  'function') {
				args['callback'] = editCallback;
			}
			IDEYATECH.util.loadPage(url, args);
		}
	};
}();

/**
 * For merging multiple checkboxes into one so it is easier to bind.
 * 
 * @class checkbox
 * @namespace IDEYATECH
 */
IDEYATECH.checkbox = function() {
	return {
		/**
		 * Merge multiple checkbox values into one string. Use this method to
		 * easily bind multiple checkboxes into one single string. Output is
		 * comma separated of checked values.
		 * 
		 * @method merge
		 * @param -
		 *            formName = name of form containing the checkboxes -
		 *            targetName = name of the hidden element to put the merged
		 *            values
		 */
        mergeSubmit : function(formName, targetName) {
            var checkBoxValues = "";
            $('#' + formName+ ' input[name=checkboxes]').each(function(index) {
                if (this.checked) {
                    checkBoxValues = checkBoxValues + this.value + ",";    
                }
            });
            if(checkBoxValues.length > 0)
                checkBoxValues = checkBoxValues.substring(0, checkBoxValues.length - 1);
            $('#' + targetName).val(checkBoxValues);
            $('#'+formName).submit();
            return true;
        }
	};
}();

/**
 * Javascript sorting by header with arrow characters
 */

IDEYATECH.sort = function() {
	return {
		sortByHeader : function(newSortFieldValue, searchFormId) {
			var sortFieldValue = $('#' + searchFormId + '-order-by').val();
			var sortFlowValue = $('#' + searchFormId + '-order-flow').val();
			var newSortFlowValue;

			if (sortFieldValue != newSortFieldValue) {
				newSortFlowValue = 'ASC';
			} else {
				if (sortFlowValue == 'DESC') {
					newSortFlowValue = 'ASC';
				} else {
					newSortFlowValue = 'DESC';
				}
			}

			$('#' + searchFormId + '-order-by').val(newSortFieldValue);
			$('#' + searchFormId + '-order-flow').val(newSortFlowValue);
			$('#' + searchFormId).submit();
		}
	};
}();

IDEYATECH.paging = function() {
	return {
		searchByPage : function(searchFormId, baseURL, pageNum) {
			$('#' + searchFormId).attr("action", baseURL + '?page=' + pageNum);
			$('#' + searchFormId).submit();
		}
	};
}();
