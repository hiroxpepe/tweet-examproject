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
import javax.inject.Inject;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.springframework.context.ApplicationContext;

import org.examproject.tweet.dto.TweetDto;
import org.examproject.tweet.entity.Tweet;
import org.examproject.tweet.repository.TweetRepository;

/**
 * @author hiroxpepe
 */
public class SimpleRecentService implements RecentService {

    private static final Log LOG = LogFactory.getLog(
        SimpleRecentService.class
    );

    private static final int RECENT_COUNT = 20;

    @Inject
    private final ApplicationContext context = null;

    @Inject
    private final Mapper mapper = null;

    @Inject
    private final TweetRepository tweetRepository = null;

    ///////////////////////////////////////////////////////////////////////////
    // public methods

    ///////////////////////////////////////////////////////////////////////////
    /**
     * get the tweet dto list by name.
     */
    @Override
    public List<TweetDto> getTweetListByName(
        String userName
    ) {
        LOG.debug("called.");
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            // get the tweet list.
            List<Tweet> tweetList = tweetRepository.findByNameOrderByDateDesc(
                userName
            );
            LOG.debug("tweet count: " + tweetList.size());

            // map the object.
            List<TweetDto> tweetDtoList = new ArrayList<TweetDto>();
            for (Tweet tweet : tweetList) {
                TweetDto tweetDto = context.getBean(TweetDto.class);
                // map the entity-object to the dto-object.
                mapper.map(
                    tweet,
                    tweetDto
                );
                tweetDtoList.add(
                    tweetDto
                );
                if (tweetDtoList.size() == RECENT_COUNT) {
                    break;
                }
            }

            LOG.debug("recent tweet count: " + tweetDtoList.size());

            stopWatch.stop();
            LOG.info("execute time: " + stopWatch.getTime() + " msec");

            return tweetDtoList;

        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}