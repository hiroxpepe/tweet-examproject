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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.context.ApplicationContext;

import org.examproject.tweet.dto.TweetDto;
import org.examproject.tweet.entity.Tweet;
import org.examproject.tweet.entity.Vocab;
import org.examproject.tweet.entity.Word;
import org.examproject.tweet.repository.TweetRepository;
import org.examproject.tweet.repository.VocabRepository;
import org.examproject.tweet.repository.WordRepository;
import org.examproject.tweet.util.DateValue;
import org.examproject.tweet.util.DayBeginDateTransformer;
import org.examproject.tweet.util.DayEndDateTransformer;
import org.springframework.beans.BeansException;

/**
 * @author hiroxpepe
 */
public class SimplePermalinkService implements PermalinkService {

    private static final Log LOG = LogFactory.getLog(
        SimplePermalinkService.class
    );

    @Inject
    private final ApplicationContext context = null;

    @Inject
    private final Mapper mapper = null;

    @Inject
    private final TweetRepository tweetRepository = null;

    @Inject
    private final VocabRepository vocabRepository = null;

    @Inject
    private final WordRepository wordRepository = null;

    ///////////////////////////////////////////////////////////////////////////
    // public methods

    ///////////////////////////////////////////////////////////////////////////
    /**
     * get the tweet dto list by statusid.
     */
    @Override
    public TweetDto getTweetByStatusId(
        Long statusId
    ) {
        LOG.debug("called.");
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            // get the tweet list.
            Tweet tweet = tweetRepository.findById(statusId);
            LOG.debug("tweet statusId: " + tweet.getId());

            // map the object.
            TweetDto tweetDto = context.getBean(TweetDto.class);

            // map the entity-object to the dto-object.
            mapper.map(
                tweet,
                tweetDto
            );

            stopWatch.stop();
            LOG.info("execute time: " + stopWatch.getTime() + " msec");

            return tweetDto;

        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * get the tweet dto list by date.
     */
    @Override
    public List<TweetDto> getTweetListByDate(
        String userName,
        Integer year,
        Integer month,
        Integer day
    ) {
        LOG.debug("called.");
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            // create a date conditions.
            Transformer beginDateTransformer = new DayBeginDateTransformer();
            Transformer endDateTransformer = new DayEndDateTransformer();
            DateValue dateValue = new DateValue(year, month, day);
            Date begin = (Date) beginDateTransformer.transform(dateValue);
            Date end = (Date) endDateTransformer.transform(dateValue);

            // get the tweet list.
            List<Tweet> tweetList = tweetRepository.findByNameAndDateBetween(
                userName,
                begin,
                end
            );
            LOG.debug("permalink tweet count: " + tweetList.size());

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
            }

            // sort by created date on desc.
            Comparator comparator = new ReverseComparator(
                new CreatedDateComparator()
            );
            Collections.sort(
                tweetDtoList,
                comparator
            );

            stopWatch.stop();
            LOG.info("execute time: " + stopWatch.getTime() + " msec");

            return tweetDtoList;

        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * get the tweet dto list by word.
     */
    @Override
    public List<TweetDto> getTweetListByWord(
        String userName,
        String text
   ) {
        LOG.debug("called.");
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            List<TweetDto> tweetDtoList = null;

            // search from multiple words.
            if (text.indexOf(' ') != -1) {

                // get the tweet list by multiple words.
                tweetDtoList = getTweetListByMultipleWords(userName, text);

            // search from single word.
            } else if (text.indexOf(' ') == -1) {

                // get the word.
                List<Word> words = wordRepository.findByText(text);

                // if not exist.
                if (words.isEmpty()) {
                    return new ArrayList<TweetDto>();
                }

                // get the tweet list by single word.
                tweetDtoList = getTweetListBySingleWord(userName, words);
            }

            // sort by created date on desc.
            Comparator comparator = new ReverseComparator(
                new CreatedDateComparator()
            );
            Collections.sort(
                tweetDtoList,
                comparator
            );

            LOG.debug("permalink tweet count: " + tweetDtoList.size());

            stopWatch.stop();
            LOG.info("execute time: " + stopWatch.getTime() + " msec");

            return tweetDtoList;

        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
    * update the entity.
    */
    @Override
    public TweetDto update(
        TweetDto tweetDto
    ) {
        LOG.debug("called.");
        try {
            // save the entity.
            Tweet tweet = context.getBean(Tweet.class);
            tweet.setId(Long.valueOf(tweetDto.getStatusId()));
            tweet.setDate(tweetDto.getCreated());
            tweet.setName(tweetDto.getUserName());
            tweet.setText(tweetDto.getRawText());
            Tweet newTweet = (Tweet) tweetRepository.save(tweet);

            // map the entity to dto.
            TweetDto newTweetDto = context.getBean(TweetDto.class);
            mapper.map(
                newTweet,
                newTweetDto
            );
            return newTweetDto;
        } catch(Exception e) {
            LOG.error("an error occurred: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // private methods.

    ///////////////////////////////////////////////////////////////////////////
    /**
    * search from single word.
    */
    private List<TweetDto> getTweetListBySingleWord(
        String userName,
        List<Word> words
    ) {
        // get the only first.
        Word word = words.get(0);

        // get the vocab list.
        List<Vocab> vocabList = vocabRepository.findByWordId(word.getId());

        LOG.debug("permalink vocab count: " + vocabList.size());

        // map the object.
        List<TweetDto> tweetDtoList = new ArrayList<TweetDto>();
        List<Long> tmpStatusIdList =  new ArrayList<Long>();
        for (Vocab vocab : vocabList) {
            Long statusId = vocab.getStatus().getId();
            if (tmpStatusIdList.contains(statusId)) {
                continue;
            }

            // get the tweet.
            Tweet tweet = tweetRepository.findById(statusId);

            // get user's tweet only.
            if (!tweet.getName().equals(userName)) {
                continue;
            }

            // map the entity-object to the dto-object.
            TweetDto tweetDto = context.getBean(TweetDto.class);
            mapper.map(
                tweet,
                tweetDto
            );
            tweetDtoList.add(
                tweetDto
            );
            tmpStatusIdList.add(statusId);
        }

        return tweetDtoList;
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
    * search from multiple words.
    */
    private List<TweetDto> getTweetListByMultipleWords(
        String userName,
        String word
    ) {
        // get the tweet list.
        List<Tweet> tweetList = tweetRepository.findByNameAndTextLike(
            userName,
            "%" + word + "%"
        );

        LOG.debug("permalink tweet by multiple words count: " + tweetList.size());

        // map the object.
        List<TweetDto> tweetDtoList = new ArrayList<TweetDto>();
        for (Tweet tweet : tweetList) {

            // map the entity-object to the dto-object.
            TweetDto tweetDto = context.getBean(TweetDto.class);
            mapper.map(
                tweet,
                tweetDto
            );
            tweetDtoList.add(
                tweetDto
            );
        }

        return tweetDtoList;
    }

    ///////////////////////////////////////////////////////////////////////////
    // private classes.

    ///////////////////////////////////////////////////////////////////////////
    /**
     * sort list by created date.
     */
    class CreatedDateComparator implements Comparator<TweetDto> {

        @Override
        public int compare(TweetDto o1, TweetDto o2 ) {
            if (o1.getCreated() == null) {
                return 0;
            }
            if (o2.getCreated() == null) {
                return 0;
            }

            int i = o1.getCreated().compareTo(o2.getCreated());
            if (i < 0) {
                return -1;
            } else if (i > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}