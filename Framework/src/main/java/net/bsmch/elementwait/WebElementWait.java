package net.bsmch.elementwait;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Clock;
import java.time.Duration;

public class WebElementWait extends FluentWait<WebElement> {
    public WebElementWait(WebElement element, long timeOutInSeconds) {
        this(
            element,
            Clock.systemDefaultZone(),
            Sleeper.SYSTEM_SLEEPER,
            timeOutInSeconds,
            DEFAULT_SLEEP_TIMEOUT);
    }

    public WebElementWait(WebElement element, long timeOutInSeconds, long sleepInMillis) {
        this(
            element,
            Clock.systemDefaultZone(),
            Sleeper.SYSTEM_SLEEPER,
            timeOutInSeconds,
            sleepInMillis);
    }

    protected WebElementWait(WebElement element,
                             Clock clock,
                             Sleeper sleeper,
                             long timeOutInSeconds,
                             long sleepTimeOut) {
        super(element, clock, sleeper);
        withTimeout(Duration.ofSeconds(timeOutInSeconds));
        pollingEvery(Duration.ofMillis(sleepTimeOut));
    }
}
