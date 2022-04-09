package com.example.xbulild.service;

import com.example.xbulild.entity.Block;
import com.example.xbulild.repository.BlockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockService {
    BlockRepository blockRepository;

    public BlockService(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    public List<Block> findAll(){
        return blockRepository.findAll();
    }

}
