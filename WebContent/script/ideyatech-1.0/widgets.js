/**
 * This source code is property of Ideyatech, Inc.
 * All rights reserved. 
 *
**/

/**
* 
* Module that handles the drag and drop of widgets.
* 
* @requires jQuery($), jQuery UI & sortable/draggable UI modules
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

	/*Default url to call in persisting our widget order*/
	var _urlOrder = "widget-persist.jspx?methodName=saveWidgetOrder&order=";
	/*Default url to call in persisting our widget status*/
	var _urlStatus = "widget-persist.jspx?methodName=saveWidgetStatus&widgetName=";

	return {		
		/**
		* Method that saves the status of a user's widget.
		* REMOVE - 0
		* SHOW (EXPAND) - 1
		* COLLAPSE (MINIMIZE) - 2
		* 
		* @param el - target element
		* @param widgetStatus - (WIDGET_STATUS_REMOVE | WIDGET_STATUS_SHOW | WIDGET_STATUS_MINIMIZE)
		*/
		executeSaveStatus: function(el,widgetStatus) {
			//get the widget name
			var widgetName = el.parentNode.id;
			//avoid memory leak...
			el = null;
			//save state..
			var append = widgetName +"&status=" +widgetStatus;
			$.ajax({
				url: _urlStatus+append
			});
		},

		/**
		 * Method that saves the order of the user's widgets.
		 * 
		 * @param event - the event that was caught
		 * @param el - the element that triggered the event
		 */
		executeOrderUpdate: function(event, el){
			if(event.target == el){
            	var object = el;
    			var widgetArrParameter;
    			setTimeout(function() {
    				widgetArrParameter = iNettuts.getWidgetArrangement(object);
    				//alert("\\|");
    				//alert(IDEYATECH.widgets.getUrlOrder()+widgetArrParameter);
    				$.ajax({
    					type: "POST",
    					url: IDEYATECH.widgets.getUrlOrder()+widgetArrParameter
    				});
    			}, 500);	
            }
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
		}
				
	}//end return
}();

/**
 * @author Script from NETTUTS.com [by James Padolsey], modified by Ideyatech, Inc.
 * Module that builds the dashboard.
 * @requires jQuery($), jQuery UI & sortable/draggable UI modules, jquery-ui-personalized-1.6rc2.min.js
 */
var iNettuts = {
    
    jQuery : $,
    
    settings : {
        columns : '.column',
        widgetSelector: '.widget',
        handleSelector: '.widget-head',
        contentSelector: '.widget-content',
        widgetDefault : {
            movable: true,
            removable: true,
            collapsible: true,
            editable: true,
            collapsed: false,
            colorClasses : ['color-yellow', 'color-red', 'color-blue', 'color-white', 'color-orange', 'color-green']
        },
        widgetIndividual : {
            intro : {
                movable: false,
                removable: false,
                collapsible: false,
                editable: false
            },
            collapse : {
            	collapsed: true
            }
        }
    },

    init : function () {
        this.addWidgetControls();
        this.makeSortable();
    },
    
    getWidgetSettings : function (id) {
        var $ = this.jQuery,
            settings = this.settings;
        return (id&&settings.widgetIndividual[id]) ? $.extend({},settings.widgetDefault,settings.widgetIndividual[id]) : settings.widgetDefault;
    },
    
    addWidgetControls : function () {
        var iNettuts = this,
            $ = this.jQuery,
            settings = this.settings;
            
        $(settings.widgetSelector, $(settings.columns)).each(function () {
            var thisWidgetSettings = iNettuts.getWidgetSettings(this.id);
			
			if (thisWidgetSettings.removable) {
                $('<a href="#" class="remove" title="Close">Close</a>').mousedown(function (e) {
                    e.stopPropagation();
                }).click(function () {
					$(this).parents(settings.widgetSelector).animate({
						opacity: 0    
					},function () {
						$(this).wrap('<div/>').parent().slideUp(function () {
							$(this).remove();
						});
					});
					IDEYATECH.widgets.executeSaveStatus(this, '0');
                    return false;
                }).prependTo($(settings.handleSelector, this));
            }        
			if(thisWidgetSettings.collapsible) {
				if (thisWidgetSettings.collapsed) {
					$('<a href="#" class="collapse" title="Show">Show</a>').mousedown(function (e) {
	                    e.stopPropagation();    
	                }).toggle(function () {
	                    $(this).css({backgroundPosition: ''})
	                        .parents(settings.widgetSelector)
	                            .find(settings.contentSelector).show();
	                    IDEYATECH.widgets.executeSaveStatus(this, '1');
	                    $(this).attr('title','Hide');
	                    return false;
	                },function () {
						$(this).css({backgroundPosition: '-38px 0'})
	                        .parents(settings.widgetSelector)
	                            .find(settings.contentSelector).hide();
						IDEYATECH.widgets.executeSaveStatus(this, '2');
	                    $(this).attr('title','Show');
						//alert(this.parentNode.id+"HIDE");
	                    return false;
	                }).appendTo($(settings.handleSelector,this));
					
					$(this).find(".collapse").css({backgroundPosition: '-38px 0'});
					$(this).find(".widget-content").hide();
				} else {
					$('<a href="#" class="collapse" title="Hide">Hide</a>').mousedown(function (e) {
	                    e.stopPropagation();    
	                }).toggle(function () {
	                    $(this).css({backgroundPosition: '-38px 0'})
	                        .parents(settings.widgetSelector)
	                            .find(settings.contentSelector).hide();
	                    IDEYATECH.widgets.executeSaveStatus(this, '2');
	                    $(this).attr('title','Show');
	                    return false;
	                },function () {
	                    $(this).css({backgroundPosition: ''})
	                        .parents(settings.widgetSelector)
	                            .find(settings.contentSelector).show();
	                    IDEYATECH.widgets.executeSaveStatus(this, '1');
	                    $(this).attr('title','Hide');
	                    return false;
	                }).appendTo($(settings.handleSelector,this));
				}
			}
        });
    },
    
    getWidgetArrangement : function (object) {
    	var arrangement = "";
        var columns = $(object).parent().parent().parent().delay(800).find('ul').each(function(){
			$(this).find(".widget-head").each(function(){
				arrangement += $(this).attr('id') + ",";
			});
			arrangement = arrangement.substring(0, arrangement.length - 1);
			arrangement += "|";
		});
        //arrangement = arrangement.substring(0, arrangement.length - 1);
        return arrangement;       
    },
    
    makeSortable : function () {
        var iNettuts = this,
            $ = this.jQuery,
            settings = this.settings,
            $sortableItems = (function () {
                var notSortable = '';
                $(settings.widgetSelector,$(settings.columns)).each(function (i) {
                    if (!iNettuts.getWidgetSettings(this.id).movable) {
                        if(!this.id) {
                            this.id = 'widget-no-id-' + i;
                        }
                        notSortable += '#' + this.id + ',';
                    }
                });
                return $('> li:not(' + notSortable + ')', settings.columns);
            })();
        
        $sortableItems.find(settings.handleSelector).css({
            cursor: 'move'
        }).mousedown(function (e) {
            $sortableItems.css({width:''});
            $(this).parent().css({
                width: $(this).parent().width() + 'px'
            });
        }).mouseup(function (e) {
            if(!$(this).parent().hasClass('dragging')) {
            	$(this).parent().css({
                    width: $(this).parent().width() + 'px'});
            } else {
                $(settings.columns).sortable('disable');
            }            
            IDEYATECH.widgets.executeOrderUpdate(e, this);            
        });

        $(settings.columns).sortable({
            items: $sortableItems,
            connectWith: $(settings.columns),
            handle: settings.handleSelector,
            placeholder: 'widget-placeholder',
            forcePlaceholderSize: true,
            revert: 300,
            delay: 100,
            opacity: 0.8,
            containment: 'document',
            start: function (e,ui) {
                $(ui.helper).addClass('dragging');
            },
            stop: function (e,ui) {
                $(ui.item).css({width:''}).removeClass('dragging');
                $(settings.columns).sortable('enable');
            }
        });
    }
};


