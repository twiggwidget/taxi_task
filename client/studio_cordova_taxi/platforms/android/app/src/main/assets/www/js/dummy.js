		//the Play Framework server will do this
        function dummyLocalMileTest(  )
		{
		    var mile     = "0.00";
		    //***inputs***
			var lat1    = document.getElementById('start-lat').value;
			var lon1    = document.getElementById('start-long').value;
			var lat2    = document.getElementById('end-lat').value;
			var lon2    = document.getElementById('end-long').value;
			var startDt = getDateTimeStart( );
			var currDt  = getDateTimeCurr( );

			//***do mile***
			mile    = distance(lat1, lon1, lat2, lon2, "M");

			return mile;
		}

		//the Play Framework server will do this
        function dummyLocalFareTest(  )
		{
		    var fare     = "0.00";
		    //***inputs***
			var lat1    = document.getElementById('start-lat').value;
			var lon1    = document.getElementById('start-long').value;
			var lat2    = document.getElementById('end-lat').value;
			var lon2    = document.getElementById('end-long').value;
			var startDt = getDateTimeStart( );
			var currDt  = getDateTimeCurr( );

			//***do fare***
			journeyTime(startDt, currDt);
			var weekday = isWeekDay(startDt);
			console.log("weekday: " + weekday);

			var tariff = 0;
			var rate   = 0;
			var mile   = dummyLocalMileTest( );
			console.log("mile: " + mile);
			if(mile < 6)
			{
				console.log("mile less than 6: " + mile);
				if (tariff_1_test(startDt))
				{
					rate = 2.74;
				}
				if (tariff_2_test(startDt))
				{
					rate = 3.37;
				}
				if (tariff_3_test(startDt))
				{
					rate = 3.96;
				}
			}
			else
			{
				//over 6 miles, new rate
				//work out 6 mile rate (save fare)
				//subtract 6 miles from total mileage
				//work out over 6 mile rate and add to saved fare

				console.log("mile more than 6: " + mile);
				rate = 3.70;
			}

			console.log("rate: " + rate);
			fare = mile * rate;
			var roundedFare = roundNumber(fare,2);

			return roundedFare;
		}

	// Saturday and Sunday else its Monday to Friday
	function isWeekDay(startDt)
	{
		var start = moment(startDt, "YYYY-M-D HH:mm:ss");
		if (start.isoWeekday() !== 6 && start.isoWeekday() !== 7)
		{
			return true;
		}
  		else return false;
	}

	function isDayTime(startDt)//05:00-20:00
	{
  		return true;
	}

	function isEveningTime(startDt)//20:00-22:00
	{
  		return true;
	}

	function isNightTime(startDt)//22:00-05:00
	{
  		return true;
	}


	function isPubHolidayTime(startDt)//Public holidays
	{
  		return true;
	}

	function journeyTime(startDt, endDt)//journeyTime
	{
		var start = moment(startDt, "YYYY-M-D HH:mm:ss");
		var end   = moment(endDt  , "YYYY-M-D HH:mm:ss");
		var diff  = end.diff(start, 'minutes');
		console.log("journeyTime: " + diff);
	}

	function isBoundaryCrossedTest(startDt)//Boundary (edge) conditions
	{
  		return false;
	}

	function tariff_1_test(startDt)
	{
		//for journeys up to 6 miles
		//Monday to Friday, 05:00-20:00
  		return true;
	}

	function tariff_2_test(startDt)
	{
		//for journeys up to 6 miles
		//Monday to Friday, 20:00-22:00
		//Saturday and Sunday, 05:00-22:00
  		return true;
	}


	function tariff_3_test(startDt)
	{
		//Every night, 22:00-05:00
		//Public holidays
  		return true;
	}

	function tariff_4_test(startDt)
	{
		//Tariff rate for journeys over 6 miles
		//Monday to Friday, 05:00-20:00
  		return true;
	}

/*
	//var xxx = Math.floor(Math.random() * 20);//randomNumberBetween0and19
	function randomWholeNum()
	{
  		return Math.random();
	}
*/