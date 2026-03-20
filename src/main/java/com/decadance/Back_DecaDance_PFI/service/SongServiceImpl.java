package com.decadance.Back_DecaDance_PFI.service;

import org.springframework.stereotype.Service;

import com.decadance.Back_DecaDance_PFI.repository.SongRepository;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository){
        this.songRepository = songRepository;
    }

    // @Override

}
