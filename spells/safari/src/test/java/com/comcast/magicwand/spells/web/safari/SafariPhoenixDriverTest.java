/**
 * Copyright 2015 Comcast Cable Communications Management, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.comcast.magicwand.spells.web.safari;

import org.mockito.Mockito;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.comcast.magicwand.builders.PhoenixDriverIngredients;

public class SafariPhoenixDriverTest {

    private class TestSafariPhoenixDriver extends SafariPhoenixDriver {
        boolean verifyIngredientsReturn;
        SafariDriver createSafariDriverReturn;

        void setVerifyIngredientsReturn(boolean expected) {
            this.verifyIngredientsReturn = expected;
        }

        void setCreateSafariDriverReturn(SafariDriver expected) {
            this.createSafariDriverReturn = expected;
        }

        @Override
        protected SafariDriver createDriver(DesiredCapabilities dc) {
            return this.createSafariDriverReturn;
        }

        @Override
        protected boolean verifyIngredients(PhoenixDriverIngredients i) {
            return this.verifyIngredientsReturn;
        }
    }

    TestSafariPhoenixDriver myTestObj;

    @BeforeMethod
    public void setupTestsVars() {
        this.myTestObj = new TestSafariPhoenixDriver();
    }

    @Test
    public void testVerifyNegative() {
        boolean actual;

        this.myTestObj.setVerifyIngredientsReturn(false);
        actual = this.myTestObj.verify(null);

        Assert.assertFalse(actual);
    }

    @Test
    public void testVerifyPositive() {
        boolean actual;
        SafariDriver sdMock = Mockito.mock(SafariDriver.class);
        PhoenixDriverIngredients iMock = Mockito.mock(PhoenixDriverIngredients.class);

        this.myTestObj.setVerifyIngredientsReturn(true);
        this.myTestObj.setCreateSafariDriverReturn(sdMock);
        actual = this.myTestObj.verify(iMock);

        Assert.assertTrue(actual);
        Assert.assertEquals(sdMock, this.myTestObj.getDriver());
    }
}
