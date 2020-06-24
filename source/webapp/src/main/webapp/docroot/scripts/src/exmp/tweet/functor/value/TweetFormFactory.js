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
 * get the value from the html form and 
 * create a json object for http post.
 * 
 * @author h.adachi
 */
exmp.tweet.functor.value.TweetFormFactory = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    create: function() {
        // convert the form data to JSON.
        var param = {};
        $($("#tweet-form").serializeArray()).each(
            function(i, v) {
                param[v.name] = v.value;
            }
        );
        return $.toJSON(param);
    }
}