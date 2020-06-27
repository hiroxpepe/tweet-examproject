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

package org.examproject.tweet.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.auth.AccessToken;

import org.examproject.tweet.dto.ProfileDto;
import org.examproject.tweet.dto.TweetDto;
import org.examproject.tweet.value.SettingValue;
import org.examproject.tweet.value.StateValue;
import org.examproject.tweet.value.TweetAuthValue;

/**
 * @author h.adachi
 */
@Service(value="tweetService")
@Scope(value="prototype")
public class SimpleTweetService implements TweetService {

    private static final Log LOG = LogFactory.getLog(
        SimpleTweetService.class
    );

    private static final int WAIT_COUNT = 5;

    private static final int WAIT_MSEC = 1800;

    private final TweetAuthValue authValue;

    private final SettingValue settinValue;

    private final StateValue stateValue;

    private TweetDto tweetDto;

    ///////////////////////////////////////////////////////////////////////////
    // constructor

    public SimpleTweetService(){
        this(
            new TweetAuthValue("", "", "", ""),
            new SettingValue("", ""),
            new StateValue("", "")
        );
    }

    public SimpleTweetService(
        TweetAuthValue authValue
    ){
        this(
            authValue,
            new SettingValue("", ""),
            new StateValue("", "")
        );
    }

    public SimpleTweetService(
        TweetAuthValue authValue,
        SettingValue settingValue
    ){
        this(
            authValue,
            settingValue,
            new StateValue("", "")
        );
    }

    public SimpleTweetService(
        TweetAuthValue authValue,
        SettingValue settingValue,
        StateValue stateValue
    ){
        this.authValue = authValue;
        this.settinValue = settingValue;
        this.stateValue = stateValue;
    }

    ///////////////////////////////////////////////////////////////////////////
    // public methods

    @Override
    public List<TweetDto> getList(String content) {
        LOG.debug("called.");

        ResponseList/*<Status>*/ responseList = null;
        List<TweetDto> tweetDtoList = null;
        try {
            // TODO: a temporary..
            for (int i = 0; i < WAIT_COUNT; i++) {
                responseList = getResponseList();
                // when the content is null, simply get a list.
                if (content.length() == 0) {
                    break;
                }
                // search from the list.
                boolean isFound = false;
                for (int j = 0; j < responseList.size(); j++) {
                    //Status status = responseList.get(j);
                    Status status = (Status) responseList.get(j);
                    // when the content is equal to the get text.
                    if (status.getText().equals(content)) {
                        isFound = true;
                        break;

                    }
                }
                if (isFound) {
                    break;
                } else {
                    // wait for the update..?
                    Thread.sleep(WAIT_MSEC);
                    LOG.debug("wait count: " + String.valueOf(i + 1));
                }
            }

            // map the value.
            tweetDtoList = new ArrayList<TweetDto>();
            //for (Status status : responseList) {
            for (Object o : responseList) {
                Status status = (Status) o;
                TweetDto tweetDto = mapStatus(status);
                tweetDtoList.add(tweetDto);
            }
            return tweetDtoList;
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TweetDto> update(String content) {
        LOG.debug("called.");
        try {
            tweetDto = mapStatus(
                updateStatus(content)
            );
            return getList(
                content
            );
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TweetDto> delete(Long statusId) {
        LOG.debug("called.");
        try {
            tweetDto = mapStatus(
                destroyStatus(statusId)
            );
            // wait for the delete..
            Thread.sleep(WAIT_MSEC);
            return getList(
                ""
            );
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TweetDto> reply(String content, Long statusId) {
        LOG.debug("called.");
        try {
            tweetDto = mapStatus(
                replyStatus(content, statusId)
            );
            return getList(
                content
            );
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TweetDto> favorite(Long statusId) {
        LOG.debug("called.");
        try {
            tweetDto = mapStatus(
                createOrDeleteFavorite(statusId)
            );
            // wait for the favorite..
            Thread.sleep(WAIT_MSEC);
            return getList(
                ""
            );
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void retweet(Long statusId) {
        LOG.debug("called.");
        try {
            tweetDto = mapStatus(
                retweetStatus(statusId)
            );
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getUserListNameList() {
        LOG.debug("called.");
        try {
            List<String> userListNameList = new ArrayList<String>();
            PagableResponseList/*<UserList>*/ userList = getUserList();
            //for (UserList list : userList) {
            for (Object o : userList) {
                UserList list = (UserList) o;
                LOG.debug("list FullName: " + list.getFullName());
                userListNameList.add(list.getFullName());
            }
            return userListNameList;
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public TweetDto getCurrent() {
        return tweetDto;
    }

    @Override
    public ProfileDto getProfile(
        String username
    ) {
        LOG.debug("called.");
        try {
            return getUserProfile(username);
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // private methods

    private Status updateStatus(String content) {
        Status status = null;
        try {
            Twitter twitter = getTwitter();
            status = twitter.updateStatus(content);
        } catch (TwitterException te) {
            LOG.error("an error occurred: " + te.getMessage());
            throw new RuntimeException(te);
        }
        return status;
    }

    private Status destroyStatus(long statusId) {
        Status status = null;
        try {
            Twitter twitter = getTwitter();
            status = twitter.destroyStatus(statusId);
        } catch (TwitterException te) {
            LOG.error("an error occurred: " + te.getMessage());
            throw new RuntimeException(te);
        }
        return status;
    }

    private Status replyStatus(String content, long statusId) {
        Status status = null;
        StatusUpdate statusUpdate = new StatusUpdate(content);
        statusUpdate.setInReplyToStatusId(statusId);
        try {
            Twitter twitter = getTwitter();
            status = twitter.updateStatus(statusUpdate);
        } catch (TwitterException te) {
            LOG.error("an error occurred: " + te.getMessage());
            throw new RuntimeException(te);
        }
        return status;
    }

    private Status createOrDeleteFavorite(long statusId) {
        Status status = null;
        try {
            Twitter twitter = getTwitter();
            status = twitter.createFavorite(statusId);
            return status;
        } catch (TwitterException te) {
            // if the message included 'You have already favorited this status'.
            String message = te.getMessage();
            // TODO: what is good in this?
            if (message.indexOf("You have already favorited this status") != -1) {
                try {
                    Twitter twitter = getTwitter();
                    status = twitter.destroyFavorite(statusId);
                    return status;
                } catch (TwitterException te1) {
                    LOG.error("an error occurred: " + te1.getMessage());
                    throw new RuntimeException(te1);
                }
            }
            LOG.error("an error occurred: " + te.getMessage());
            throw new RuntimeException(te);
        }
    }

    private Status retweetStatus(long statusId) {
        Status status = null;
        try {
            Twitter twitter = getTwitter();
            status = twitter.retweetStatus(statusId);
        } catch (TwitterException te) {
            LOG.error("an error occurred: " + te.getMessage());
            throw new RuntimeException(te);
        }
        return status;
    }

    private PagableResponseList/*<UserList>*/ getUserList() {
        long cursol = -1;
        try {
            Twitter twitter = getTwitter();
            //return twitter.getUserLists(twitter.getScreenName(), cursol);
            return twitter.getUserListMemberships(twitter.getScreenName(), cursol);
        } catch (TwitterException te) {
            throw new RuntimeException(te);
        }
    }

    private ResponseList/*<Status>*/ getResponseList() {
        LOG.debug("called.");

        long cursol = -1;
        int listId = 0;
        int count = 120;
        int pageNumber = 1;

        Paging paging = new Paging(
            pageNumber,
            count
        );

        try {
            // TODO: polymorphism to here? -> plugin.
            // home
            if (settinValue.getResponseListMode().equals("home")) {
                Twitter twitter = getTwitter();
                return twitter.getHomeTimeline(
                    new Paging(
                        1,
                        200
                    )
                );
            }

            // user
            if (settinValue.getResponseListMode().equals("user")) {
                Twitter twitter = getTwitter();
                return twitter.getUserTimeline(paging);
            }

            // list
            if (settinValue.getResponseListMode().equals("list")) {
                if (settinValue.getUserListName().length() != 0) {
                    Twitter twitter = getTwitter();
                    //PagableResponseList/*<UserList>*/ lists = twitter.getUserLists(
                    //    twitter.getScreenName(),
                    //    cursol
                    //);
                    PagableResponseList/*<UserList>*/ lists = twitter.getUserListMemberships(
                        twitter.getScreenName(),
                        cursol
                    );
                    //for (UserList list : lists) {
                    for (Object o : lists) {
                        UserList list = (UserList) o;
                        String listFullName = list.getFullName();
                        if (listFullName.equals(settinValue.getUserListName())) {
                            //listId = list.getId();
                            listId = (int) list.getId();
                            return twitter.getUserListStatuses(listId, paging);
                        }
                    }
                }
            }

            // default..
            Twitter twitter = getTwitter();
            return twitter.getHomeTimeline();

        } catch (TwitterException te) {
            // TODO: transition to an error page here?
            throw new RuntimeException(te);
        }
    }

    private TweetDto mapStatus(Status status) {
        TweetDto tweetDto = new TweetDto();
        tweetDto.setUserProfileImageURL(status.getUser().getProfileImageURL().toString());
        tweetDto.setUserName(status.getUser().getScreenName());
        tweetDto.setText(StringEscapeUtils.escapeHtml(status.getText()));
        tweetDto.setRawText(status.getText());
        tweetDto.setStatusId(String.valueOf(status.getId()));
        tweetDto.setCreated(status.getCreatedAt());
        tweetDto.setIsFavorited(status.isFavorited());
        tweetDto.setIsRetweetedByMe(status.isRetweetedByMe());
        return tweetDto;
    }

    private ProfileDto getUserProfile(String username) {
        try {
            Twitter twitter = getTwitter();
            User user = twitter.showUser(username);
            ProfileDto profileDto = new ProfileDto();
            profileDto.setScreenName(user.getScreenName());
            profileDto.setImageURL(user.getProfileImageURL().toString());
            profileDto.setDescription(user.getDescription());
            return profileDto;
        } catch (TwitterException te) {
            throw new RuntimeException(te);
        }
    }

    private Twitter getTwitter() {
        TwitterFactory factory = new TwitterFactory();
        Twitter twitter = factory.getInstance();
        twitter.setOAuthAccessToken(new AccessToken(
            authValue.getOauthToken(),
            authValue.getOauthTokenSecret()
        ));
        return twitter;
    }

}