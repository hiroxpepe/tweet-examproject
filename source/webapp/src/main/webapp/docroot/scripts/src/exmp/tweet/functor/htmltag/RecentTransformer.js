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
exmp.tweet.functor.htmltag.RecentTransformer = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    transform: function(obj) {
        
        var constantValue = exmp.tweet.conf.ConstantValue;
        
        // dynamically generate an html tags.
        var html = "<div>";
        if (obj.tweetModelList != null) {
            for (var i = 0; i < obj.tweetModelList.length; i++) {
                
                // get the value.
                var name = obj.tweetModelList[i].userName;
                var text = obj.tweetModelList[i].text;
                var statusId = obj.tweetModelList[i].statusId;
                
                // create an html tag.
                html += "<div class='recent-div'>" +
                            "<a href='" + constantValue.siteDomain + "/tweet/" + name + "/" + statusId + ".html" + 
                                "' id='recent-a-" + statusId + 
                                "' class='recent-a'>" + this._getShortText(text) + 
                            "</a>" +
                        "</div>";
            }
            if (obj.tweetModelList.length == 0) {
                html += "<div class='nodata'>No Data.</div>";
            }
            html += "</div>";
        }
        return html;
    },
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    _getShortText: function(text) {
        var markOmitted = "";
        if (text.length > 17) {
            markOmitted = " ..."
        }
        var shorted = text.substring(0, 17)
        return shorted + markOmitted;
    }
    
}