package net.bsmch.elementwait;

import com.google.common.base.Function;
import org.openqa.selenium.SearchContext;

public interface ElementCondition<T> extends Function<SearchContext, T> {}
