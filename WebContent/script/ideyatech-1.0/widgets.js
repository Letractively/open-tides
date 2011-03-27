/**
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 *	Needed YUI files: 
 *		<script type="text/javascript" src="js/yui/yahoo-dom-event.js"></script>
 *		<script type="text/javascript" src="js/yui/animation-min.js"></script>
 *		<script type="text/javascript" src="js/yui/dragdrop-min.js"></script>
 *
 *	If you intend to persist the order or state of each widget (thru _supportsPersistState=true) , you will need this library/file(s):
 *       <script src="js/yui/connection/connection-min.js" type="text/javascript"></script>
**/

if (!yConnect) {var yConnect = YAHOO.util.Connect;}

if (!yDom) {var yDom = YAHOO.util.Dom;}
if (!yEvent) {var yEvent = YAHOO.util.Event;}
if (!yDDM) {var yDDM = YAHOO.util.DragDropMgr;}
if (!IDEYATECH) {IDEYATECH = {};}
if (!IDEYATECH.example) {IDEYATECH.example = YAHOO.example;}


/**
* Module that handles the drag and drop of widgets.
* @requires YAHOO.util.Dom
* @requires YAHOO.util.Event
* @class widgets
* @namespace IDEYATECH.widgets
**/
IDEYATECH.widgets = function() {
	/*status state of widget */
	const WIDGET_STATUS_REMOVE = 0;
	const WIDGET_STATUS_SHOW = 1;
	const WIDGET_STATUS_MINIMIZE = 2;
	/*Classname marker for widget minimize */
	var _minimizeWidgetClassName = "widget-collapse";
	/*Classname marker for widget show */
	var _showWidgetClassName = "widget-expand";
	/*Classname marker for none DD target Element */
	var _notDDTargetClassName = "not-dd-target";
	
	/*Do we persist the state draggable objects?*/
	var _supportsPersistState = false;
	/*Default url to call in persisting our widget order*/
	var _urlOrder = "widget-persist.jspx?methodName=saveWidgetOrder&order=";
	/*Default url to call in persisting our widget status*/
	var _urlStatus = "widget-persist.jspx?methodName=saveWidgetStatus&widgetName=";
	/*Does our widgets have a handle? */
	var _supportsHandle = false;
	/*Classname marker for handle */
	var _handleClassName = "tides-dd-handle";
	/*Classname marker for drag and drop widget */
	var _widgetBoxClassName = "widget-box";
	/*Main container name/id of our draggable objects and undraggable targets*/
	var _dashBoardContainerName = "dashboard-main";
	/*Array of DD targets (These are the column in our drag & drop dashboard)*/
	var _ddTargetElems = [];
	
	/*Default callback*/
	var _callback = {
			  success: function(o) {/*success handler code*/},
			  failure: function(o) {/*failure handler code*/},
			  argument: ['Call: default callback']
			};
	
	
	/**
	* Private method that initialize the elements DD target.
    * @method _initDDTargets
    * @param arrElem - array's of target element
    */
	var _initDDTargets = function(arrElem) {
		for ( var i = 0; i < arrElem.length; i++) {
			if (!yDom.hasClass(arrElem[i],_notDDTargetClassName)) {
				new YAHOO.util.DDTarget(arrElem[i].id);
			}
		}
	};
	
	/**
	* Private method that initialize the drag and drop elements. We put it on the list so that we
	* could easily shared their events. (dragdrop,dragstart,etc)
    * @method _initDDList
    * @param arrElem - array of target element
    */
	var _initDDList = function(arrElem) {
		for ( var i = 0; i < arrElem.length; i++) {
			var el = arrElem[i];
			var ddElem = new IDEYATECH.example.DDList(el.id);
			//does we support handle?
			if (_supportsHandle) {
				//find the handle by getting the class name
				var arrHandle = yDom.getElementsByClassName(_handleClassName,"div",el);
				//get the first of the list 
				if (arrHandle[0]) {
					ddElem.setHandleElId(arrHandle[0])
				}//skip if elements doesn't exist
			}
		}
	};
	
	
	/**
	* Private method that gets the elements DD target.
    * @method _getDDTargetElems
	* @param el - main div element
    * @return arrElem - array of target element
    */  
	var _getDDTargetElems = function(el) { 
		var arrElem = yDom.getChildren(el);
		return arrElem;
	
	};
	
	
	/**
	* Private method that gets the elements DD target.
    * @method _getArrayOfDDElements
	* @param arrTargetElem - array of target elements
    * @return arrDDElem - array list of Drag and drop elem
    */  
	var _getArrayOfDDElements = function(arrTargetElem) { 
	
		var arrElem = [];
		var ctr = 0;
		
		for ( var i = 0; i < arrTargetElem.length; i++) {
			var arrTemp = yDom.getChildren(arrTargetElem[i]);
			for (var j=0;j<arrTemp.length;j++) {
				arrElem[ctr] = arrTemp[j];
				ctr++;
			}
		
		}
		return arrElem;
	};
	
	return {
		/**
		* Initializer method for our drag and drop class
		* Public core method that is responsible for attaching events to our dashboard
		* @param mainDivId - the main dashboard container
		*/
		init: function (mainDivId) {
			if(mainDivId) {_dashBoardContainerName = mainDivId;}
			var mainDiv = yDom.get(_dashBoardContainerName);
			_ddTargetElems = _getDDTargetElems(mainDiv);
			_initDDTargets(_ddTargetElems);
			var arrayOfDDElements = _getArrayOfDDElements(_ddTargetElems);
			_initDDList(arrayOfDDElements);	
		},
		
		/**
		* Helper public method that saves the status of widget
		* TODO: maybe we could overide this method outside?
		* @param el - target element
		* @param widgetStatus - (WIDGET_STATUS_REMOVE | WIDGET_STATUS_SHOW | WIDGET_STATUS_MINIMIZE)
		*/
		executeSaveStatus: function(el,widgetStatus) {
			if (_supportsPersistState) {
				//get the widget id
				var widgetId = el.parentNode.parentNode.id;
				
				if (widgetStatus == WIDGET_STATUS_REMOVE) {
					yDom.get(widgetId).parentNode.removeChild(yDom.get(widgetId));
				} else if (widgetStatus == WIDGET_STATUS_SHOW) {
					/*modify event listeners*/
					el.onclick = null;
					el.onclick = function() {IDEYATECH.widgets.executeSaveStatus(this,WIDGET_STATUS_MINIMIZE);}

					yDom.replaceClass(el,_showWidgetClassName,_minimizeWidgetClassName);
					yDom.getNextSibling(el.parentNode).style.display ="";
				} else if (widgetStatus == WIDGET_STATUS_MINIMIZE) {
					/*modify event listeners*/
					el.onclick = null;
					el.onclick = function() {IDEYATECH.widgets.executeSaveStatus(this,WIDGET_STATUS_SHOW);}
					yDom.replaceClass(el,_minimizeWidgetClassName,_showWidgetClassName);
					yDom.getNextSibling(el.parentNode).style.display ="none";
				}
				//avoid memory leak...
				el = null;
				//save state..
				var append = widgetId +"&status=" +widgetStatus;
				yConnect.asyncRequest('GET', _urlStatus+append, IDEYATECH.widgets.getCallback());
			}
		},
		
		
		/**
		* Getter method for _widgetBoxClassName
		*/
		getWidgetBoxClassName: function() {
			return _widgetBoxClassName;
		},
		/**
		* Setter method for _widgetBoxClassName
		*/
		setWidgetBoxClassName: function(newClassName) {
			_widgetBoxClassName = newClassName;
		},
		/**
		* Getter method for _handleClassName
		*/
		getHandleClassName: function() {
			return _handleClassName;
		},
		/**
		* Setter method for _handleClassName
		*/
		setHandleClassName: function(newClassName) {
			_handleClassName = newClassName;
		},
		/**
		* do we support persistence?
		*/
		isSupportsPersistState: function() {
			return _supportsPersistState;
		},
		/**
		* Setter method for _supportsPersistState
		*/
		setSupportsPersistState: function(boolVal) {
			_supportsPersistState = boolVal;
		},
		
		/**
		* do we support widget handle?
		*/
		isSupportsHandle: function() {
			return _supportsHandle;
		},
		/**
		* Setter method for _supportsHandle
		*/
		setSupportsHandle: function(boolVal) {
			_supportsHandle = boolVal;
		},
		
		/**
		* Getter method for _dashBoardContainerName
		*/
		getDashBoardContainerName: function() {
			return _dashBoardContainerName;
		},
		/**
		* Setter method for _dashBoardContainerName
		*/
		setDashBoardContainerName: function(newDashBoardContainerName) {
			_dashBoardContainerName = newDashBoardContainerName;
		},
		/**
		* Getter method for _ddTargetElems
		*/
		getDDTargetElems: function() {
			return _ddTargetElems;
		},
		/**
		* Setter method for _ddTargetElems
		*/
		setDDTargetElems: function(arrElems) {
			_ddTargetElems = arrElems;
		},
		
		/**
		* Getter method for _urlOrder
		*/
		getUrlOrder: function() {
			return _urlOrder;
		},
		/**
		* Setter method for _urlOrder
		*/
		setUrlOrder: function(sUrl) {
			_urlOrder = sUrl;
		},
		/**
		* Getter method for _urlStatus
		*/
		getUrlStatus: function() {
			return _urlStatus;
		},
		/**
		* Setter method for _urlStatus
		*/
		setUrlStatus: function(sUrl) {
			_urlStatus = sUrl;
		},
		
		/**
		* Getter method for _callback
		*/
		getCallback: function() {
			return _callback;
		},
		/**
		* Setter method for callback
		*/
		setCallback: function(myCallback) {
			_callback = myCallback;
		}
				
	}//end return
	
}();



/**
* This class handles the Drag and drop objects. Use this on drag and drop elements to provide one way of implementing events
* on the drag and drop functionality. This class uses YAHOO.example module.
* @requires YAHOO.util.Dom
* @class DDList
* @namespace IDEYATECH.example
*/
IDEYATECH.example.DDList = function(id, sGroup, config) { 
	 
    IDEYATECH.example.DDList.superclass.constructor.call(this, id, sGroup, config);  
	//disable the logging
	//this.logger = this.logger || YAHOO; 
	var el = this.getDragEl(); 
	yDom.setStyle(el, "opacity", 0.67); // The proxy is slightly transparent 
 
	this.goingUp = false; 
	this.lastY = 0; 
	
	/*Used in persisting our DD order*/
	this.startColumn = null;
	this.endColumn = null;
	this.isDragOver = false;
};

/**
* This module is extending IDEYATECH.example.DDList to YAHOO.util.DDProxy
* Implement events at YAHOO.util.DDProxy
* @description IDEYATECH.example.DDList extends YAHOO.util.DDProxy
*/
YAHOO.extend(IDEYATECH.example.DDList, YAHOO.util.DDProxy, {

    startDrag: function(x, y) {
        //this.logger.log(this.id + " startDrag");

        // make the proxy look like the source element
        var dragEl = this.getDragEl();
        var clickEl = this.getEl();
        yDom.setStyle(clickEl, "visibility", "hidden");

        dragEl.innerHTML = clickEl.innerHTML;

        yDom.setStyle(dragEl, "color", yDom.getStyle(clickEl, "color"));
        yDom.setStyle(dragEl, "backgroundColor", yDom.getStyle(clickEl, "backgroundColor"));
        yDom.setStyle(dragEl, "border", "2px solid gray");
        
        /*Used in persisting our DD order*/
        this.startColumn = clickEl.parentNode.id;
		this.isDragOver = false;
    },

    endDrag: function(e) {

        var srcEl = this.getEl();
        var proxy = this.getDragEl();

        // Show the proxy element and animate it to the src element's location
        yDom.setStyle(proxy, "visibility", "");
        var a = new YAHOO.util.Motion( 
            proxy, { 
                points: { 
                    to: yDom.getXY(srcEl)
                }
            }, 
            0.2, 
            YAHOO.util.Easing.easeOut 
        )
        var proxyid = proxy.id;
        var thisid = this.id;

        // Hide the proxy and show the source element when finished with the animation
        a.onComplete.subscribe(function() {
                yDom.setStyle(proxyid, "visibility", "hidden");
                yDom.setStyle(thisid, "visibility", "");
            });
        a.animate();
    },

    onDragDrop: function(e, id) {

        // If there is one drop interaction, the li was dropped either on the list,
        // or it was dropped on the current location of the source element.
        if (yDDM.interactionInfo.drop.length === 1) {

            // The position of the cursor at the time of the drop (YAHOO.util.Point)
            var pt = yDDM.interactionInfo.point; 

            // The region occupied by the source element at the time of the drop
            var region = yDDM.interactionInfo.sourceRegion; 

            // Check to see if we are over the source element's location.  We will
            // append to the bottom of the list once we are sure it was a drop in
            // the negative space (the area of the list without any list items)
            if (!region.intersect(pt)) {
               // var destEl = yDom.get(id);
                var destDD = yDDM.getDDById(id);
               // destEl.appendChild(this.getEl());
                destDD.isEmpty = false;
                yDDM.refreshCache();
            }

        }
        
        /*Used in persisting our DD order*/
        this.endColumn = id;
        var needToSave = false;
        if (this.isDragOver){needToSave =true;}
        if (this.startColumn != this.endColumn){needToSave =true;}
        
        //Do we persist the widgets state (shown/hide, order, etc)
        if (IDEYATECH.widgets.isSupportsPersistState() && needToSave) {
        	var arrTargetElem = IDEYATECH.widgets.getDDTargetElems();
        	
        	var arrElem = [];
        	var newArr = [];
			var ctr = 0;
			
			for ( var i = 0; i < arrTargetElem.length; i++) {
				var arrTemp = yDom.getChildren(arrTargetElem[i]);
				var jTempArr = [];
				for (var j=0;j<arrTemp.length;j++) {
					arrElem[ctr] = arrTemp[j].id;
					jTempArr[j] = arrTemp[j].id;
					ctr++;
				}
				newArr[i] = jTempArr;
			}
			var orderVal = newArr.join("|");
			//save the order
			yConnect.asyncRequest('GET', IDEYATECH.widgets.getUrlOrder() + orderVal, IDEYATECH.widgets.getCallback());
        }
    },

    onDrag: function(e) {

        // Keep track of the direction of the drag for use during onDragOver
        var y = yEvent.getPageY(e);

        if (y < this.lastY) {
            this.goingUp = true;
        } else if (y > this.lastY) {
            this.goingUp = false;
        }

        this.lastY = y;
    },

    onDragOver: function(e, id) {
    
        var srcEl = this.getEl();
        var destEl = yDom.get(id);

        // We are only concerned with div's that have class = _widgetBoxClassName
        if (destEl.nodeName.toLowerCase() == "div" && yDom.hasClass(destEl,IDEYATECH.widgets.getWidgetBoxClassName())) {
            var orig_p = srcEl.parentNode;
            var p = destEl.parentNode;

            if (this.goingUp) {
                p.insertBefore(srcEl, destEl); // insert above
            } else {
                p.insertBefore(srcEl, destEl.nextSibling); // insert below
            }
            yDDM.refreshCache();
            /*Used in persisting our DD order*/
            this.isDragOver = true;
        }
    }
});
