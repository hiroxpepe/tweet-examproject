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
 * send http request for the setting.
 *
 * @author h.adachi
 */
exmp.tweet.functor.request.SettingClosure = {

    ///////////////////////////////////////////////////////////////////////////
    // public methods

    execute: function(obj) {
        console.log("setting begin.");

        var constantValue = exmp.tweet.conf.ConstantValue;

        // create an ajax object.
        new $.ajax({
            url: constantValue.siteDomain + "/setting.json",
            type: "POST",
            data: obj,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",

            // callback function of the success.
            success: function(data, dataType) {

                // if get a error from the response.
                if (data.isError) {
                    console.log("application error occurred.");
                    return;
                }
            },

            // callback function of the error.
            error: function(XMLHttpRequest, textStatus, errorThrown) {

                // ** success in this class method. **
                // because, this ajax requests are redirected as normal.
                if (XMLHttpRequest.status == 200) {
                    console.log("setting complete.");

                    // reload
                    location.reload();

                    return;
                }

                console.log("http request error occurred.");
            }
        });
    }
}