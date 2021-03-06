/*
 * Copyright 2018 Alfresco Software, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *    http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.alfresco.event.gateway.config;

import org.alfresco.event.gateway.config.amqp.AmqpToProperties;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * Messaging External Properties.
 *
 * @author Jared Ottley
 */
@Configuration
public class ExternalPropertiesConfig
{
    private static final Logger logger = LoggerFactory.getLogger(ExternalPropertiesConfig.class);
    
    @Value("${messaging.external.host}")
    private String host;
    @Value("${messaging.external.port}")
    private String port;

    @Autowired
    private AmqpToProperties amqpToProperties;

    public String getExternalUrl()
    {
        String externalUrl;
        
        if (host.isEmpty() && port.isEmpty())
        {
            externalUrl = "amqp://" + amqpToProperties.getHost() + ":" + amqpToProperties.getPort();
        }
        else
        {
            externalUrl = "amqp://" + host + ":" + port;
        }

        String[] schemes = {"amqp"};
        UrlValidator urlValidator = new UrlValidator(schemes, UrlValidator.ALLOW_LOCAL_URLS);

        if (!urlValidator.isValid(externalUrl))
        {
            logger.info("The external url is invalid: " + externalUrl);
            return null;
        }

        return externalUrl;
    }
}