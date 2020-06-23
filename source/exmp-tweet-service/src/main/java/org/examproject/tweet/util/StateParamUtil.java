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

package org.examproject.tweet.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author hiroxpepe
 */
public class StateParamUtil {
    
    ///////////////////////////////////////////////////////////////////////////
    // field.
    
    private static final Log LOG = LogFactory.getLog(
        StateParamUtil.class
    );
    
    private final List<String> paramList;
    
    private final Map<String, String> paramMap;
    
    ///////////////////////////////////////////////////////////////////////////
    // constructor.
    
    private StateParamUtil(String paramString) {
        String[] paramArray = StringUtils.split(paramString, ";");
        paramList = Arrays.asList(paramArray);
        if (paramList.isEmpty()) {
            paramMap = null;
            return;
        }
        
        // set parameter to map.
        paramMap = new HashMap<String, String>();
        for (String pram : paramList) {
            String[] keyAndValueArray = StringUtils.split(pram, "=");
            String key = keyAndValueArray[0];
            String value = keyAndValueArray[1];
            // DDDDD
            LOG.info("key: " + key);
            LOG.info("value: " + value);
            // DDDDD
            paramMap.put(key, value);
        }
    }
    
    public static StateParamUtil getNewInstance(String paramString) {
        return new StateParamUtil(paramString);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // public method.
    
    public String getCalendarYear() {
        if (paramMap.isEmpty()) {
            return "";
        }
        String value = paramMap.get("calendar-year");
        // DDDDD
        LOG.info("calendar-year: " + value);
        // DDDDD
        return value;
    }
                    
    public String getCalendarMonth() {
        if (paramMap.isEmpty()) {
            return "";
        }
        String value = paramMap.get("calendar-month");
        // DDDDD
        LOG.info("calendar-month: " + value);
        // DDDDD
        return value;
    }
    
}