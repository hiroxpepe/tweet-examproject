/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

///////////////////////////////////////////////////////////////////////////////
/**
 * a functor class of the application.
 * get the value for calendar.
 * 
 * @author hiroxpepe
 */
exmp.tweet.functor.value.CalendarParamFactory = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    create: function() {

        var currentYear = $("#calendar-year").val();
        var currentMonth = $("#calendar-month").val();

        // new http request.
        if (currentYear == "" && currentMonth == "") {

            // get the cookie year and month.
            var siteParam = $.cookie('__twenglish_state_param');
            if (siteParam != null) {
                var paramArray = siteParam.split(";");
                var year = "";
                var month = "";
                for (var i = 0; i < paramArray.length; i++) {
                    var paramString = paramArray[i];
                    var keyAndValue = paramString.split("=");
                    var key = keyAndValue[0];
                    var value = keyAndValue[1];
                    if (key == "calendar-year") {
                        console.log("find year in cookie - year: " + value);
                        year = value;
                    } else if (key == "calendar-month") {
                        console.log("find month in cookie - month: " + value);
                        month = value;
                    }
                }
                
                // set year and month as current.
                $("#calendar-year").val(year);
                $("#calendar-month").val(month);
                
                // get cookie year and month.
                var param0 = {
                    year: year,
                    month: month
                }
                return param0;
                
            } else {
                // get real year and month.
                var date = new Date();
                var year = date.getFullYear();
                var month = date.getMonth() + 1;

                // set year and month as current.
                $("#calendar-year").val(year);
                $("#calendar-month").val(month);

                // create as current parameter.
                var param1 = {
                    year: year,
                    month: month
                }
                return param1;
            }
        
        // ajax request.
        } else {
            // get current year and month.
            var param2 = {
                year: currentYear,
                month: currentMonth
            }
            return param2;
        }
    }
}