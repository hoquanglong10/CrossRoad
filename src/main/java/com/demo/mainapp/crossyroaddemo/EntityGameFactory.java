package com.demo.mainapp.crossyroaddemo;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.texture;

public final class EntityGameFactory implements EntityFactory {
    public Entity newBackground(){
        return entityBuilder()
                .at(-10, -10)
                .view(texture("assets/minecraft-crossy-road.png"))
                .zIndex(-500)
                .build();
    }
}
