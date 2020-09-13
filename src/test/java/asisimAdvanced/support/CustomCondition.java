package asisimAdvanced.support;

import org.openqa.selenium.SearchContext;

import java.util.function.Function;

public interface CustomCondition<T> extends Function<SearchContext, T> {
}
