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
 * a util class of cookie.
 * 
 * @author h.adachi
 */
exmp.tweet.util.StateCookieUtil = {
    
    key: "__twenglish_state_param",

    setYearAndMonth: function (year, month) {
        var siteParam = $.cookie(this.key);
        var newSiteParam = "";
        if (siteParam != null) {
            var paramArray = siteParam.split(";");
            for (var i = 0; i < paramArray.length; i++) {
                var paramString = paramArray[i];
                var keyAndValue = paramString.split("=");
                var key = keyAndValue[0];
                var value = keyAndValue[1];
                if ((key != "calendar-year") && (key != "calendar-month")) {
                    if (newSiteParam != "") {
                        newSiteParam += ";";
                    }
                    newSiteParam += key + "=" + value;
                } else if (key == "calendar-year") {
                    if (newSiteParam != "") {
                        newSiteParam += ";";
                    }
                    newSiteParam += "calendar-year=" + year;
                } else if (key == "calendar-month") {
                    if (newSiteParam != "") {
                        newSiteParam += ";";
                    }
                    newSiteParam += "calendar-month=" + month;
                }
            }
            $.cookie(
                '__twenglish_state_param',
                newSiteParam,
                {
                    expires: 7,
                    path: '/' 
                }
            );
        } else {
            var param = "calendar-year=" + year + ";" + "calendar-month=" + month;
            $.cookie(
                '__twenglish_state_param',
                param,
                {
                    expires: 7,
                    path: '/'
                }
            );
        }
    }
}