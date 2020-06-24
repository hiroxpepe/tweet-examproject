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
 * this class is a transformer that json data get by
 * ajax http requests and convert to html tags.
 * 
 * @author h.adachi
 */
exmp.tweet.functor.htmltag.TagcrowdTransformer = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    transform: function(obj) {
        
        // dynamically generate an html tags.
        var html = "<div>";
        if (obj.tagcrowdModelList != null) {
            for (var i = 0; i < obj.tagcrowdModelList.length; i++) {
                
                // get the value
                var text = obj.tagcrowdModelList[i].text;
                var link = obj.tagcrowdModelList[i].linkUrl;
                link = link.replace(/'/, "&apos;");
                var count = obj.tagcrowdModelList[i].count;

                // create an html tag.
                html += "<span class='tagcrowd-span'>" +
                            "<a href='" + link + 
                                "' title='" + count + " tweets" +
                                "' class='tagcrowd-a'>" + text + 
                            "</a>" +
                        "</span>";
            }
            if (obj.tagcrowdModelList.length == 0) {
                html += "<div class='nodata'>No Data.</div>";
            }
            html += "</div>";
        }
        return html;
    }
        
}