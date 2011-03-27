<%@ page contentType="text/html;utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ot" uri="http://www.ideyatech.com/tides"%>
<%@ taglib prefix="idy" tagdir="/WEB-INF/tags"%>

<idy:header title_webpage="label.welcome">
</idy:header>

<!-- BODY -->
<div id="bd">
    <div class="yui-g">
    	<div class="main" id="systemmonitor">
	        <div class="title-wrapper">
	        	<div class="title" id="systemmonitor-title"><span><spring:message code="label.system-monitor" /></span></div>
	        </div>
	        
	        <div class="contents">
	        	<b>Available Processors:</b><br/>
	        	Available Processors: ${processors}<br/>		                                                                            
	        	<hr/>
	        	<b>Memory Usage:</b><br/>
	        	Heap Size: <fmt:formatNumber type="number" pattern="#,###,###" value="${heapSize}" /> Kb<br/>		                                                                            
	        	Maximum Heap Size: <fmt:formatNumber type="number" pattern="#,###,###" value="${heapMaxSize}" /> Kb<br/>		                                                                            
	        	Free Heap Size: <fmt:formatNumber type="number" pattern="#,###,###" value="${heapFreeSize}" /> Kb<br/>	
	        	<hr/>
	       <c:if test="${empty statistics}">
	       		Database statistics is not enabled.
	       </c:if>                                                                            
	       <c:if test="${not empty statistics}">	                                                                            
	        	<b>Database Statistics:</b><br/>
	        	Start Time: <fmt:formatDate value="${startDate}" pattern="MM/dd/yyyy HH:mm" /> <br/> 
	        	Transactions: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.transactionCount}" /> <br/>		                                                                            
	        	Successful Transactions: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.successfulTransactionCount}" /> <br/>		                                                                            
	        	Optimistic Lock Failures: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.optimisticFailureCount}" /> <br/>		                                                                            
	        	Flushes: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.flushCount}" /> <br/>		                                                                            
	        	Connections Obtained: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.connectCount}" /> <br/>		                                                                            
	        	<hr/>                                                        
	        	Sessions Opened: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.sessionOpenCount}" /> <br/>		                                                                            
	        	Sessions Closed: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.sessionCloseCount}" /> <br/> 
	        	<hr/>	                                                                       
	        	Statements Prepared: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.prepareStatementCount}" /> <br/>		                                                                            
	        	Statements Closed: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.closeStatementCount}" /> <br/>		                                                                            
	        	<hr/>		                                                                            
	        	Entities Loaded: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.entityLoadCount}" /> <br/>		                                                                            
	        	Entities Updated: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.entityUpdateCount}" /> <br/>		                                                                            
	        	Entities Inserted: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.entityInsertCount}" /> <br/>		                                                                            
	        	Entities Deleted: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.entityDeleteCount}" /> <br/>		                                                                            
	        	Entities Fetched: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.entityFetchCount}" /> <br/>		                                                                            
	        	<hr/>		                                                                            
	        	Collections Loaded: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.collectionLoadCount}" /> <br/>		                                                                            
	        	Collections Updated: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.collectionUpdateCount}" /> <br/>		                                                                            
	        	Collections Removed: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.collectionRemoveCount}" /> <br/>		                                                                            
	        	Collections Recreated: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.collectionRecreateCount}" /> <br/>		                                                                            
	        	Collections Fetched: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.collectionFetchCount}" /> <br/>		                                                                            
	        	<hr/>		                   
	        	<b>Cache Statistics:</b><br/>                                    	                                                         
	        	Second Level Cache Puts: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.secondLevelCachePutCount}" /> <br/>		                                                                            
	        	Second Level Cache Hits: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.secondLevelCacheHitCount}" /> <br/>		                                                                            
	        	Second Level Cache Misses: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.secondLevelCacheMissCount}" /> <br/>		                                                                            
	        	<hr/>		                                                                            
	        	Queries Executed: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.queryExecutionCount}" /> <br/>		                                                                            
	        	Query Cache Puts: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.queryCachePutCount}" /> <br/>		                                                                            
	        	Query Cache Hits: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.queryCacheHitCount}" /> <br/>		                                                                            
	        	Query Cache Misses: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.queryCacheMissCount}" /> <br/>		                                                                            
	        	Max Query Time: <fmt:formatNumber type="number" pattern="#,###,###" value="${statistics.queryExecutionMaxTime}" /> ms.<br/>		                                                                            
	        	Max Query Time SQL String: ${statistics.queryExecutionMaxTimeQueryString}<br/>		                                                                            
	        </c:if>
	        </div>                                
		</div><!-- END OF main -->
	</div><!-- END OF yui-g -->
</div>
<idy:footer />


