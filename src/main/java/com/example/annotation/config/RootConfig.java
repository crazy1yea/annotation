package com.example.annotation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@ComponentScan(value="com.example.annotation",excludeFilters= {
		@Filter(type=FilterType.ANNOTATION,classes= {Controller.class})
})
public class RootConfig {

}
