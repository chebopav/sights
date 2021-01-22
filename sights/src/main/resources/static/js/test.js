console.log("test");

.done(function(cities){
      var citiesArray = JSON.parse(cities);
      let citiesOptions = '';
      citiesArray.forEach((city) => {
        citiesOptions =+ `<option value="${city.id}">${city.name}</option>`;
      });
      $('#city').html( citiesOptions );
    }).fail(function() {
      alert( "error" );
    });