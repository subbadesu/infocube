// Builds the HTML Table out of myList.
	
function buildPortfolioHtmlTable(selector) {
	var temparray;
	$.ajax({
         url: "http://localhost:8080/RiskFrontEnd/webapi/Portfolio",
         dataType: "json",
         async: false
	}).done(function(data) {
				temparray = data;
				data.forEach(function (arrayElem){ 
	 			    });

	 		  });
	var myList = $.ajax({
        url: "http://localhost:8080/RiskFrontEnd/webapi/Portfolio",
        dataType: "json",
        async: false
	}).done;
	 
	var columns = addAllColumnHeaders(temparray, selector);
	for (var i = 0; i < temparray.length; i++) {
		var row$ = $('<tr/>');
		for (var colIndex = 0; colIndex < columns.length; colIndex++) {
			var cellValue = temparray[i][columns[colIndex]];

			if (cellValue == null) {
				cellValue = "";
			}

			row$.append($('<td/>').html(cellValue));
		}
		$(selector).append(row$);
	}
}

// Adds a header row to the table and returns the set of columns.
// Need to do union of keys from all records as some records may not contain
// all records
function addAllColumnHeaders(myList,selector) {
	var columnSet = [];
	var headerTr$ = $('<tr/>');

	for (var i = 0; i < myList.length; i++) {
		var rowHash = myList[i];
		for ( var key in rowHash) {
			window.alert("key value" + key);
			if ($.inArray(key, columnSet) == -1) {
				columnSet.push(key);
				headerTr$.append($('<th/>').html(key));
			}
		}
	}
	$(selector).append(headerTr$);

	return columnSet;
}
// select options
function populateSelectOptions(selector){
$.ajax({
    url: "http://localhost:8080/RiskFrontEnd/webapi/Portfolio",
    dataType: "json",
    async: false
}).done(function(data) {
			var dropdown = document.getElementById("selectNumber");
			data.forEach(function (arrayElem){ 
			var optn = document.createElement("OPTION");
			optn.text = arrayElem.portfolioName;
			optn.value = arrayElem.portfolioId;
			dropdown.options.add(optn);
		        });

		  });
}

