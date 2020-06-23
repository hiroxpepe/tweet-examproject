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
 * update the html table of the calendar.
 * 
 * @author hiroxpepe
 */
exmp.tweet.functor.dhtml.CalendarUpdataClosure = {
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    execute: function(obj) {
        
        var transformer = exmp.tweet.functor.htmltag.CalendarTransformer;
        
        $("#tweet-calendar").html(
            transformer.transform(
                obj
            )
        );
        
        // set the calendar's event handler.
        var controller = exmp.tweet.core.Controller;
        
        $("#calendar-back").click(function() {
            controller._doCalendarBackButtonOnClick();
        });
        
        $("#calendar-forward").click(function() {
            controller._doCalendarForwardButtonOnClick();
        });
    }
}