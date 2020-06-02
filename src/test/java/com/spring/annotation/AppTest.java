package com.spring.annotation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.annotation.config.ConfigInitializer;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ConfigInitializer.class })
public class AppTest {
	@Autowired
	private DataSource dataSource;

	@Test
	public void getContextConfiguration() {
		System.out.println(dataSource);
	}

	public static void main(String[] args) {
		Map<String, Integer> map = new LinkedHashMap<>();
		map.put("A", 10);
		map.put("B", 7);
		map.put("C", 5);
		map.put("D", 4);
		for (int i = 0; i < 10; i++) {
			System.out.println(">>>> " + (i+1));
			map.entrySet().stream().sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue()))
					.collect(Collectors.toList())
					.forEach(ele -> System.out.print(ele.getKey() + "=" + ele.getValue()+"\t"));
			int n = 0;
			for (Entry<String, Integer> entry : map.entrySet()) {
				if (n == 0) {
					System.out.println(entry.getKey()+" <-> "+entry.getValue());
					entry.setValue(entry.getValue() - 3);
				} else {
					entry.setValue(entry.getValue() + 1);
				}
				n++;
			}
			System.out.println("");
			map.entrySet().stream().collect(Collectors.toList())
			.forEach(ele -> System.out.print(ele.getKey() + "=" + ele.getValue()+"\t"));
			System.out.println("");
		}
	}
}
