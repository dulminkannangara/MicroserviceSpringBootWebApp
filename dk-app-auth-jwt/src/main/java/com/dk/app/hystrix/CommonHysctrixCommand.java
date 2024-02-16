package com.dk.app.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.function.Supplier;

public class CommonHysctrixCommand<T> extends HystrixCommand<T> {

    Supplier<T> execute;
    Supplier<T> fallback;

    public CommonHysctrixCommand(String group, Supplier<T> execute) {
        super(HystrixCommandGroupKey.Factory.asKey(group));
        this.execute = execute;
    }

    public CommonHysctrixCommand(Setter config, Supplier<T> execute) {
        super(config);
        this.execute = execute;
    }

    public CommonHysctrixCommand(String group, Supplier<T> execute, Supplier<T> fallback) {
        super(HystrixCommandGroupKey.Factory.asKey(group));
        this.execute = execute;
        this.fallback = fallback;
    }

    public CommonHysctrixCommand(Setter config, Supplier<T> execute, Supplier<T> fallback) {
        super(config);
        this.execute = execute;
        this.fallback = fallback;
    }


    @Override
    protected T run() throws Exception {
        return execute.get();
    }

    @Override
    protected T getFallback() {
        if (fallback != null) {
            return fallback.get();
        }
        return super.getFallback();
    }
}
