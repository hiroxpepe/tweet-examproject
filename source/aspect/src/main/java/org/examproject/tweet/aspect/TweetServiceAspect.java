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

package org.examproject.tweet.aspect;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationContext;

import org.examproject.tweet.dto.TweetDto;
import org.examproject.tweet.service.PermalinkService;
import org.examproject.tweet.service.TagcrowdService;
import org.examproject.tweet.service.TweetService;
import org.examproject.tweet.util.IsContainJaKanaCodePredicate;
import org.examproject.tweet.util.IsContainKrHangulCodePredicate;
import org.examproject.tweet.util.IsContainOfParticularWords;
import org.examproject.tweet.util.IsEnglishSentencePredicate;
import org.examproject.tweet.util.IsStartedOfParticularWords;

/**
 * @author hiroxpepe
 */
@Aspect
public class TweetServiceAspect {
    
    private static final Log LOG = LogFactory.getLog(
        TweetServiceAspect.class
    );
    
    private static final String PERMALINK_SERVICE_BEAN_ID = "permalinkService";
    
    private static final String TAGCROWD_SERVICE_BEAN_ID = "tagcrowdService";
    
    @Inject
    private final ApplicationContext context = null;
    
    ///////////////////////////////////////////////////////////////////////////
    // public methods
    
    @Before("execution(* org.examproject.tweet.service.TweetService.update(..))")
    public void updateBefore(JoinPoint jp) {
        LOG.debug("called.");
        
        Object args[] = jp.getArgs();
        String content = (String) args[0];
        LOG.debug("content: " + content);
    }
    
    @After("execution(* org.examproject.tweet.service.TweetService.update(..))")
    public void updateAfter(JoinPoint jp) {
        LOG.debug("called.");
        
        // a checker of sentence.
        Predicate jaPredicate = new IsContainJaKanaCodePredicate();
        Predicate krPredicate = new IsContainKrHangulCodePredicate();
        
        // get tweetService.
        TweetService tweetService = (TweetService) jp.getThis();
        TweetDto tweetDto = tweetService.getCurrent();
        String text = tweetDto.getText();
        
        // contain japanese.
        if (jaPredicate.evaluate(text)) {
            return;
        }
        // contain korean.
        if (krPredicate.evaluate(text)) {
            return;
        }
        
        // do permalinkService.
        PermalinkService permalinkService = (PermalinkService) context.getBean(
            PERMALINK_SERVICE_BEAN_ID
        );
        permalinkService.update(
            tweetDto
        );
        
        // do tagcrowdService.
        TagcrowdService tagcrowdService = (TagcrowdService) context.getBean(
            TAGCROWD_SERVICE_BEAN_ID
        );
        tagcrowdService.update(
            tweetDto
        );
    }
    
    @Around("execution(* org.examproject.tweet.service.TweetService.getList(..))")
    @SuppressWarnings("unchecked")
    public Object getListAround(ProceedingJoinPoint pjp) throws Throwable {
        LOG.debug("called.");
        
        // do the process.
        Object result = pjp.proceed();
        
        // get filtered result.
        List<TweetDto> filteredResult = getFilteredResult(result);
       
        // must return the object.
        return filteredResult;
    }
    
    @Around("execution(* org.examproject.tweet.service.TweetService.update(..))")
    @SuppressWarnings("unchecked")
    public Object updateAround(ProceedingJoinPoint pjp) throws Throwable {
        LOG.debug("called.");
        
        // do the process.
        Object result = pjp.proceed();
        
        // get filtered result.
        List<TweetDto> filteredResult = getFilteredResult(result);
       
        // must return the object.
        return filteredResult;
    }
    
    @Around("execution(* org.examproject.tweet.service.TweetService.reply(..))")
    @SuppressWarnings("unchecked")
    public Object replyAround(ProceedingJoinPoint pjp) throws Throwable {
        LOG.debug("called.");
        
        // do the process.
        Object result = pjp.proceed();
        
        // get filtered result.
        List<TweetDto> filteredResult = getFilteredResult(result);
       
        // must return the object.
        return filteredResult;
    }
    
    @Around("execution(* org.examproject.tweet.service.TweetService.favorite(..))")
    @SuppressWarnings("unchecked")
    public Object favoriteAround(ProceedingJoinPoint pjp) throws Throwable {
        LOG.debug("called.");
        
        // do the process.
        Object result = pjp.proceed();
        
        // get filtered result.
        List<TweetDto> filteredResult = getFilteredResult(result);
       
        // must return the object.
        return filteredResult;
    }
    
    @Around("execution(* org.examproject.tweet.service.TweetService.delete(..))")
    @SuppressWarnings("unchecked")
    public Object deleteAround(ProceedingJoinPoint pjp) throws Throwable {
        LOG.debug("called.");
        
        // do the process.
        Object result = pjp.proceed();
        
        // get filtered result.
        List<TweetDto> filteredResult = getFilteredResult(result);
       
        // must return the object.
        return filteredResult;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    
    private List<TweetDto> getFilteredResult(Object result) {
        
        // a checker of sentence.
        Predicate jaPredicate = new IsContainJaKanaCodePredicate();
        Predicate krPredicate = new IsContainKrHangulCodePredicate();
        Predicate spwPredicate = new IsStartedOfParticularWords();
        Predicate cpwPredicate = new IsContainOfParticularWords();
        Predicate esPredicate = new IsEnglishSentencePredicate();
        
        // create a new list for return.
        List<TweetDto> filteredResult = new ArrayList<TweetDto>();
        
        // search all of tweets.
        for (TweetDto tweetDto : (List<TweetDto>) result) {
           
            // get a tweet.
            String text = tweetDto.getText();
           
            // if it contain Japanese word.
            if (jaPredicate.evaluate(text)) {
                continue;
            }
            // if it contain Korean word.
            if (krPredicate.evaluate(text)) {
                continue;
            }
            // if it is started from particular words like "RT" or "@".
            if (spwPredicate.evaluate(text)) {
                continue;
            }
            // if it is contain particular words like "RT" or "@".
            if (cpwPredicate.evaluate(text)) {
                continue;
            }
            
            // if this sentence is not in English.
            if (!esPredicate.evaluate(text)) {
                continue;
            }
            
            // other, set the tweet to new list.
            filteredResult.add(tweetDto);
        }
        
        return filteredResult;
    }
    
}