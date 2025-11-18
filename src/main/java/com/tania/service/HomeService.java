package com.tania.service;

import java.util.List;

import com.tania.model.Home;
import com.tania.model.HomeCategory;

public interface HomeService {

    Home creatHomePageData(List<HomeCategory> categories);

}
