function getObjectFromId(array, id) {
	for (var int = 0; int < array.length; int++) {
		var every = array[int];
		if (every.id == id) {
			return every;
		}
	}
}

function json2csv(dataArray) {
	var output = '';
	for (var i = 0; i < dataArray.length; i++) {
		if (i == 0) {
			output = stringify(getComponents(dataArray, i, 'key'));
		}
		output += stringify(getComponents(dataArray, i, 'value'));
	}
	return output;
}

function stringify(json) {
	var stringified = JSON.stringify(json);
	stringified = stringified.replace('[', '');
	stringified = stringified.replace(']', '');

	stringified += "\n";
	return stringified;
}

function getComponents(array, i, comp) {
	var returnArray = [];
	var keyed = '';
	$.each(array[i], function(key, value) {
		if (comp == 'key') {
			var k = convertKey(key);
			if (k) {
				returnArray.push(k);
			} else {
				keyed = key;
				$.each(array, function(key1, value1) {
					delete value1[keyed];
				});
			}
		} else if (comp == 'value') {
			returnArray.push(value);
		}
	});
	return returnArray;
}

function convertKey(key) {
	var mapper = {
			'id': 'Item ID',
			'name': 'Item Name',
			'itemType': 'Item Type',
			'weight': 'Weight',
			'supplierDesc': 'Supplier',
			'categoryDesc': 'Category',
			'stockTypeDesc': 'Stock Type',
			'lendToDesc': 'Lend/Sold to',
			'lendDate': 'Lend/Sale Date',
			'lendDescription': 'Lend Description',
			'sold': 'Sold',
			'createDate': 'Purchase Date',
			'updateDate': 'Update Date',
			'status': 'Status'
	};
	return mapper[key];
}