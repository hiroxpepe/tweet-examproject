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
 * a controller class of the application.
 * 
 * @author hiroxpepe
 */
exmp.tweet.core.Controller = window;{
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    /**
     * the initialization method of the Controller class.
     * this method should be called.
     */
    exmp.tweet.core.Controller.init = function() {
        
        var controller = exmp.tweet.core.Controller;
        
        controller._initializeComponent();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // event handler methods
    
    /**
     * an event handler that called when 
     * the button of tweet is clicked.
     */
    exmp.tweet.core.Controller._doTweetButtonOnClick = function() {
        
        var updateClosure = exmp.tweet.functor.request.TweetUpdateClosure;
        var replyClosure = exmp.tweet.functor.request.TweetReplyClosure;
        
        ///////////////////////////////////////////////////
        // update the tweet status.
        
        // not reply or simple reply
        if (($("#reply-status-id").val() == "") && ($("#reply-user-name").val() == "")) {
            updateClosure.execute({
                content: $("#tweet").val(),
                userId: $("#user-id").val()
            });
            return;
        }
        
        // reply to selected status
        if (($("#reply-status-id").val()) && ($("#reply-user-name").val())) {
            replyClosure.execute({
                content: $("#tweet").val(),
                userId: $("#user-id").val(),
                statusId: $("#reply-status-id").val()
            })
            return;
        }
    }
    
    /**
     * an event handler that called when 
     * the button of setting is clicked.
     */
    exmp.tweet.core.Controller._doSettingButtonOnClick = function() {
        
        var settingClosure = exmp.tweet.functor.request.SettingClosure;
        var formFactory = exmp.tweet.functor.value.TweetFormFactory;
        
        // do the setting.
        settingClosure.execute(
            formFactory.create()
        );
    }
    
    /**
     * an event handler that called when 
     * the button of calendar back is clicked.
     */
    exmp.tweet.core.Controller._doCalendarBackButtonOnClick = function() {
        
        var calendarClosure = exmp.tweet.functor.request.CalendarClosure;
        var cookieUtil = exmp.tweet.util.StateCookieUtil;
        
        var year = $("#calendar-year").val();
        var month = $("#calendar-month").val();
        
        // decrement calendar parameter.
        if (month == "1") {
            year = parseInt(year) - 1;
            month = 12;
        } else {
            month = parseInt(month) - 1;
        }
        
        // set to div.
        $("#calendar-year").val(year);
        $("#calendar-month").val(month);
        
        // set to the cookie.
        cookieUtil.setYearAndMonth(year, month);
        
        // get the calendar.
        calendarClosure.execute({
            userId: $("#user-id").val(),
            year: year,
            month: month
        });
    }
    
    /**
     * an event handler that called when 
     * the button of calendar forward is clicked.
     */
    exmp.tweet.core.Controller._doCalendarForwardButtonOnClick = function() {
        
        var calendarClosure = exmp.tweet.functor.request.CalendarClosure;
        var cookieUtil = exmp.tweet.util.StateCookieUtil;
        
        var year = $("#calendar-year").val();
        var month = $("#calendar-month").val();
        
        // increment calendar parameter.
        if (month == "12") {
            year = parseInt(year) + 1;
            month = 1;
        } else {
            month = parseInt(month) + 1;
        }
        
        // set to div.
        $("#calendar-year").val(year);
        $("#calendar-month").val(month);
        
        // set to the cookie.
        cookieUtil.setYearAndMonth(year, month);
        
        // get the calendar.
        calendarClosure.execute({
            userId: $("#user-id").val(),
            year: year,
            month: month
        });
    }
    
    /**
     * an event handler that called when 
     * the button of search is clicked.
     */
    exmp.tweet.core.Controller._doSearchButtonOnClick = function() {
        
        var constantValue = exmp.tweet.conf.ConstantValue;
        
        var userName = $("#screen-name").val();
        var searchText = $("#search-text").val();
        
        // request the permalink page.
        if (searchText != "") {
            location.replace(
                constantValue.siteDomain + 
                "/word/" + userName + "/" + 
                searchText + ".html"
            );
        }
    }
    
    /**
     * an event handler that called when 
     * the select of response list mode is changed.
     */
    exmp.tweet.core.Controller._responseListModeOnChange = function() {
        var responseListMode = $("#response-list-mode").val();
        if (responseListMode == "list") {
            $("#user-list-name").removeAttr("disabled");
        } else {
            $("#user-list-name").attr("disabled", "disabled");
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    /**
     * an event handler that called when
     * the div of tirle is clicked.
     */
    exmp.tweet.core.Controller._headerTitleDivOnClick = function() {
        $("div.container")
            .toggleClass(
                "wide", 300
            );
    }
    
    /**
     * initializes a div of the tabs area.
     */
    exmp.tweet.core.Controller._initializeTabsDiv = function() {
        $("div.tab-content div.tab").hide();
        $("div.tab-content div.tab:first").show();
        $("div.tab-content ul li:first").addClass("active");
        $("div.tab-content ul li a").click(function(){
            $("div.tab-content ul li").removeClass("active");
            $(this).parent().addClass("active");
            var currentTab = $(this).attr("href");
            $("div.tab-content div.tab").hide();
            $(currentTab).show();
            return false;
        });
    }
    
    /**
     * initializes a div of entry list.
     * an http request of ajax for get the tweet data.
     */
    exmp.tweet.core.Controller._initializeTweetListDiv = function() {
        
        var listClosure = exmp.tweet.functor.request.TweetListClosure;
        
        // get the tweet list.
        var pageUrl = location.href;
        if (!(pageUrl.indexOf("index.html") == -1)) {
            if ($("#user-id").val()) {
                listClosure.execute({
                    userId: $("#user-id").val()
                });
            }
        }
    }
    
    /**
     * initializes a div of entry list.
     * an http request of ajax for get the sidebar data.
     */
    exmp.tweet.core.Controller._initializeSidebar = function() {
        
        var calendarClosure = exmp.tweet.functor.request.CalendarClosure;
        var tagcrowdClosure = exmp.tweet.functor.request.TagcrowdClosure;
        var recentClosure = exmp.tweet.functor.request.RecentClosure;

        var calendarParamFactory = exmp.tweet.functor.value.CalendarParamFactory;
        var calendarParam = calendarParamFactory.create();

        // get the calendar.
        calendarClosure.execute({
            userId: $("#user-id").val(),
            year: calendarParam.year,
            month: calendarParam.month
        });
        
        // get the tagcrowd.
        tagcrowdClosure.execute({
           userId: $("#user-id").val()
        });
        
        // get the recent.
        recentClosure.execute({
           userId: $("#user-id").val()
        });
    }
    
    /**
     * initializes a response list mode select of form.
     */
    exmp.tweet.core.Controller._initializeResponseListModeSelect = function() {
        $("#response-list-mode").append($('<option value="home">home</option>'));
        $("#response-list-mode").append($('<option value="user">user</option>'));
        $("#response-list-mode").append($('<option value="list">list</option>'));
    }
    
    /**
     * initializes a permalink html.
     */
    exmp.tweet.core.Controller._initializePermalink = function() {
        
        // set highlights.
        var pageUrl = location.href;
        if ((pageUrl.indexOf("index.html") == -1)) {
            $("p.permalink-text").highlight($("div.permalink-word").text());
        }
    }
    
    /**
     * initialize a component of the view class.
     */
    exmp.tweet.core.Controller._initializeComponent = function() {
        
        var controller = exmp.tweet.core.Controller;
        
        // calls for the initialization methods.
        
        controller._initializeSidebar();
        
        controller._initializeTabsDiv();
        
        controller._initializeTweetListDiv();
        
        controller._initializeResponseListModeSelect();
        
        controller._initializePermalink();
        
        // set the control's event handler.
        
        $("#tweet").keyup(function(event) {
            // clear reply param.
            // TODO: search user name?
            if ($("#tweet").val() == "") {
                $("#reply-status-id").val("")
                $("#reply-user-name").val("")
                console.log("reply-status-id: ");
                console.log("reply-user-name: ");
            }
        });
        
        $("#tweet-button").click(function() { 
            controller._doTweetButtonOnClick();
        });
        
        $("#setting-button").click(function() {
            controller._doSettingButtonOnClick();
        });
        
        $("#search-button").click(function() {
            controller._doSearchButtonOnClick();
        });
        
        $("span.header-title").click(function() {
            controller._headerTitleDivOnClick();
        });
        
        $("#response-list-mode").change(function() {
            controller._responseListModeOnChange();
        });
        
        // and do a some initialize.
        
        // profile
        $("#user-description").hide();
        $("#user-show-profile").toggle(
            function () {
                $("#user-description").show();
            },
            function () {
                $("#user-description").hide();
            }
        );
        
    }
}