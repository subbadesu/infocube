
function drawChart(portfolioID) {
	var PnL = [ [ 'ID', 'PnL' ] ];
	window.alert("PortfoioID" );
	$.ajax({					
		url : "http://localhost:8080/RiskFrontEnd/webapi/Portfolio/VaR/1",
		dataType : "json",
		async : false
	}).done(function(data) {
		data.forEach(function(arrayElem) {
			var valueToPush = new Array();
			valueToPush[0] = arrayElem.iD;
			valueToPush[1] = arrayElem.value;
			PnL.push(valueToPush);
		});

	});

	var data = google.visualization.arrayToDataTable(PnL);
	var options = {
		title : 'ID PnL ',
		hAxis : {
			title : 'ID',
			minValue : 0,
			maxValue : 15
		},
		vAxis : {
			title : 'PnL',
			minValue : 0,
			maxValue : 15
		},
		legend : 'none'
	};

	var chart = new google.visualization.ScatterChart(document
			.getElementById('chart_div'));

	chart.draw(data, options);
}