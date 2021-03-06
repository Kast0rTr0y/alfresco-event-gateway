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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExternalPropertiesConfigTest
{    
    @Autowired
    ExternalPropertiesConfig externalPropertiesConfig;

    @Test
    public void getExternalUrl()
    {
        assertEquals("amqp://localhost:5672", externalPropertiesConfig.getExternalUrl());
    }

    @Test
    //Should default to values provided by AmqpToProperties
    public void propertiesNotSet()
    {
        ReflectionTestUtils.setField(externalPropertiesConfig, "host", "");
        ReflectionTestUtils.setField(externalPropertiesConfig, "port", "");
        
        assertEquals("amqp://localhost:5672", externalPropertiesConfig.getExternalUrl());
    }

    @Test
    public void OverrideGetExternalUrl()
    {
        ReflectionTestUtils.setField(externalPropertiesConfig, "host", "alfresco.com");
        ReflectionTestUtils.setField(externalPropertiesConfig, "port", "6161");
        
        assertEquals("amqp://alfresco.com:6161", externalPropertiesConfig.getExternalUrl());
    }

    @Test
    public void InvalidHostGetExternalUrl()
    {
        ReflectionTestUtils.setField(externalPropertiesConfig, "host", "event:gateway");
        ReflectionTestUtils.setField(externalPropertiesConfig, "port", "6161");
        
        assertEquals(null, externalPropertiesConfig.getExternalUrl());
    }
}