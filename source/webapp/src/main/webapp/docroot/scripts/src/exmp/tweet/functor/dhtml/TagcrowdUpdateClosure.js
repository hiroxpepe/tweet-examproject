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
 * update the html table of the tagcrowd.
 * 
 * @author hiroxpepe
 */
exmp.tweet.functor.dhtml.TagcrowdUpdateClosure = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        
        var transformer = exmp.tweet.functor.htmltag.TagcrowdTransformer;
        
        $("#tweet-tagcrowd").html(
            transformer.transform(
                obj
            )
        );

        // add the tagcrowd height to the content min height.
        var minHeight = $("div.content").css("min-height").replace("px", "");
        var newMinHeight = parseInt(minHeight) + parseInt($("div.tagcrowd-content").height());
        $("div.content").css("min-height", newMinHeight);
    }
}