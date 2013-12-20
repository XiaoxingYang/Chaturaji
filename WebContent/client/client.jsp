<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<% 
	String gameId = request.getParameter("id");
	String name = request.getParameter("create");
	String nb_ais = request.getParameter("nb_ais");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>HTML5 Canvas - 4-Player Chess</title>
		<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
		
		
		<script src="http://code.jquery.com/jquery.js"></script>
		<script src="bootstrap/js/bootstrap.min.js"></script>
		<script src='scripts/connection.js'></script>
		<script src='scripts/4pchess.js'></script>
		<script src='scripts/processInterface.js'></script>
		
		<script>
		
			$('#status a').click(function (e) {
				e.preventDefault();
				$(this).tab('show');
			})
			$('#moves a').click(function (e) {
				e.preventDefault();
				$(this).tab('show');
			})
			$('#moves a').click(function (e) {
				e.preventDefault();
				$(this).tab('show');
			})
		
			function init(){
				//connectToServer("ws://batch1.doc.ic.ac.uk:8080/Chaturaji/WSHandler"+"?ais=1");
				<%if (gameId != null) {%> 
					connectToServer("ws://batch1.doc.ic.ac.uk:8080/Chaturaji/WSHandler?id=<%=gameId%>");
				<%} else if (name != null && nb_ais !=null){%>
					connectToServer("ws://batch1.doc.ic.ac.uk:8080/Chaturaji/WSHandler?create=<%=name%>&nb_ais=<%=nb_ais%>");
				<%}%>
				document.getElementById("chatInput").focus();
				draw();
				//setInterval(focusInput, 200);
				
				
			
			}
			
			
			function scrollDownLog(){
				var statusLog = document.getElementById("statusLog");
				statusLog.scrollTop = statusLog.scrollHeight - statusLog.clientHeight;
				var moveLog = document.getElementById("moveLog");
				moveLog.scrollTop = moveLog.scrollHeight - moveLog.clientHeight;
				var chatLog = document.getElementById("chatLog");
				chatLog.scrollTop = chatLog.scrollHeight - chatLog.clientHeight;
				
				document.getElementById("chatInput").focus();
			}
			
			
			function focusInput(){
				document.getElementById("chatInput").focus();
			}
			
			
					</script>
	</head>
	<body bgcolor='#E7F1FF'onload='init();'><center>
	</br>
	<div class="container" style="padding-left:20px">
	
		<div class="row" style="height: 100px;">
			<div class="span1"></div>
			<div class="span2"></div>
			<div class="span2">
					<div class="bs-docs-example bs-docs-example-popover" style="background:#fff">
							<div class="popover top">
								<div class="arrow"></div>
								<h3 id="topTitle">Player Top</h3>
								<div class="popover-content">
								  <p id="topScore"> Score</p>
								</div>
							 </div>
					</div>
			</div>
			<div class="span3"></div>			
			<div class="span4"></div>
		</div>
		
		<div class="row">
			<div class="span1">		<div class="bs-docs-example bs-docs-example-popover" style="background:#fff">
					<div class="popover left">
						<div class="arrow"></div>
						<h3 id="leftTitle">Player Left</h3>
						<div class="popover-content">
						  <p id="leftScore"> Score</p>
						</div>
					 </div>
				</div></div>
			<div class="span4">
				<div id="board" class="chessBoard" style="position:relative; width: 400px; height: 400px">
					<canvas id="chess" width="400" height="400"></canvas>
				</div>
			</div>
			<div class="span2" style="height: 100%">
					<div class="bs-docs-example bs-docs-example-popover" style="padding-top: 200px; background:#fff">
							<div class="popover right">
								<div class="arrow"></div>
								<h3 id="rightTitle">Player Right</h3>
								<div class="popover-content">
								  <p id="rightScore"> Score</p>
								</div>
							 </div>
					</div>	
					
			</div>
		
			<div class="span3">
			 
						<ul class="nav nav-tabs">
							<li class="active">
								<a href="#status" data-toggle="tab">Status</a>
							</li>
							<li><a href="#moves" data-toggle="tab">Moves</a></li>
							<li><a href="#chat" data-toggle="tab">Chat</a></li>
						</ul>
					
					<div id="my-tab-content" class="tab-content">			
						<div class="tab-pane" id="chat">	
							<textarea id="chatLog" style="height: 150px; width: 95%; resize:none" readonly></textarea>
						</div>

						<div class="tab-pane" id="moves">
							<textarea id="moveLog" style="height: 150px; width: 95%; resize:none" readonly></textarea>
						</div>
					
						<div class="tab-pane active" id="status">	
							<textarea id="statusLog" style="height: 150px; width: 95%; resize:none" readonly></textarea>
						</div>
				
						<div id="chatInputDiv" style="position:relative">
							<input type='text' id="chatInput" style="width: 95%">
						</div>
					</div>	
			</div>
		</div>
		
		<div class="row">
			<div class="span1"></div>
			<div class="span2"><div class="bs-docs-example bs-docs-example-popover" style="padding-top: 15px; background:#fff">
							<div class="popover bottom">
								<div class="arrow"></div>
								<h3  id="bottomTitle">Player Bottom</h3>
								<div class="popover-content">
								  <p id="bottomScore"> Score</p>
								</div>
							 </div>
					</div></div>
			<div class="span3"></div>
			<div class="span2"></div>
			<div class="span4"></div>
			
		</div>
		
	
	</div>

	
		

	</body>
	
</html>