function getObjectFromId(array, id) {
    for (var int = 0; int < array.length; int++) {
        var every = array[int];
        if (every.id == id) {
            return every;
        }
    }
}