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
 * update the setting tab.
 * 
 * @author h.adachi
 */
exmp.tweet.functor.dhtml.SettingTabUpdateClosure = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        // get setting parameters and set to div.
        var responseListMode = $.cookie("__twenglish_response_list_mode");
        var userListName = $.cookie("__twenglish_user_list_name");
        if (responseListMode == "list") {
            $("#response-list-mode").val(responseListMode);
            $("#user-list-name").val(userListName);
        } else {
            $("#response-list-mode").val(responseListMode);
            $("#user-list-name").attr("disabled", "disabled");
        }
    }
}