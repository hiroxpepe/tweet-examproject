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
exmp.tweet.functor.htmltag.CalendarTransformer = {
    
    _daynames: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    transform: function(obj) {
        
        // TODO: need null if user new.
        
        var calendarParamFactory = exmp.tweet.functor.value.CalendarParamFactory;
        var calendarParam = calendarParamFactory.create();
        
        // dynamically generate an html tags.
        if (obj.calendarModelList != null) {
            // set calendar parameter.
            return this._getCalendar(
                calendarParam.year,
                calendarParam.month,
                $('#tweet-calendar'),
                obj
            );
        }
        return null;
    },
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    _getCalendar: function(year, month, jelem, obj) {
        return this._getCalendarTag(
            year,
            month,
            jelem.get(0),
            obj
        );
    },
    
    _getLeap: function(year) {
        return year % 4 ? 0 : year % 100 ? 1 : year % 400 ? 0 : 1;
    },
    
    _createCalendarArray: function(year) {
        var months = [31, 28 + this._getLeap(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        var result = [];
        for (var m = 0; m < 12; m++) {
            result[m] = [];
            var dofw1 = (new Date(year, m, 1, 0, 0, 0)).getDay();
            for (var d = 1; d <= months[m]; d++) {
                result[m][d + dofw1 - 1] = d;
            }
        }
        return result;
    },
    
    _createCalendarMonthly: function(year, month, obj) {
        var m = month - 1;
        var calendar = this._createCalendarArray(year);
        var table = document.createElement('table');
        // header
        var tr = document.createElement('tr');
        for (var d = 0; d < 7; d++) {
            var th = document.createElement('th');
            th.innerHTML = th.className = this._daynames[d];
            tr.appendChild(th);
        }
        var thead = document.createElement('thead');
        thead.appendChild(tr);
        
        // body
        var tbody = document.createElement('tbody');
        for (var d = 0, l = calendar[m].length; d < l; d++) {
            if (d % 7 == 0) { 
                tr = document.createElement('tr');
            }
            var td = document.createElement('td');
            if (calendar[m][d]) {
                var day = calendar[m][d];
                var link = this._getLinkUrl(day, obj);
                var count = this._getTweetCount(day, obj);
                if (link) {
                    td.innerHTML = 
                        "<a class='day-a' href='" + link + "' title='" + count + " tweets'>" + 
                            day + 
                        "</a>";
                }
                else {
                    td.innerHTML = day;
                }
                td.className = this._daynames[d % 7].toLowerCase();
            }
            tr.appendChild(td);
            if (d % 7 == 6) {
                tbody.appendChild(tr);
            }
        }
        tbody.appendChild(tr);
        table.className = 'calendar-table';
        
        // caption
        var caption = document.createElement('caption');
        caption.innerHTML = 
            "<table class='calendar-caption-table'>" + 
                "<tr>" + 
                    "<td>" + "<div id='calendar-back'>&lt;</div>" + "</td>" +
                    "<td>" + "<div id='calendar-title'>" + year + '.' + (month) + "</div></td>" +
                    "<td>" + "<div id='calendar-forward'>&gt;</div>" + "</td>" +
                "</tr>" +
            "</table>";
        
        table.appendChild(caption);
        table.appendChild(thead);
        table.appendChild(tbody);
        return table;
    },
    
    _getCalendarTag: function(year, month, element, obj) {
        element.innerHTML = "";
        var calendarTable = this._createCalendarMonthly(year, month, obj);
        element.appendChild(calendarTable);
        return element.innerHTML;
    },
    
    _getLinkUrl: function(day, obj) {
        for (var i = 0; i < obj.calendarModelList.length; i++) {
            if ((obj.calendarModelList[i].day == day) && 
                (obj.calendarModelList[i].isExist == true)) {
                return obj.calendarModelList[i].linkUrl;
            }
        }
        return null;
    },
    
    _getTweetCount: function(day, obj) {
        for (var i = 0; i < obj.calendarModelList.length; i++) {
            if ((obj.calendarModelList[i].day == day) && 
                (obj.calendarModelList[i].isExist == true)) {
                return obj.calendarModelList[i].count;
            }
        }
        return null;
    }
}