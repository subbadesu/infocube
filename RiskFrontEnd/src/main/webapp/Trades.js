// Builds the HTML Table out of myList.
	
function buildTradesHtmlTable(selector) {
	var temparray;
	$.ajax({
         url: "http://localhost:8080/RiskFrontEnd/webapi/Trade/1",
         dataType: "json",
         async: false
	}).done(function(data) {
				temparray = data;
				data.forEach(function (arrayElem){ 
	 			    });

	 		  });
	 
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
