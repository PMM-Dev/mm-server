package com.kwon770.mm.domain.restaurant;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRestaurant is a Querydsl query type for Restaurant
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRestaurant extends EntityPathBase<Restaurant> {

    private static final long serialVersionUID = -1190120868L;

    public static final QRestaurant restaurant = new QRestaurant("restaurant");

    public final BooleanPath deliveryable = createBoolean("deliveryable");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.kwon770.mm.RestaurantLocation> location = createEnum("location", com.kwon770.mm.RestaurantLocation.class);

    public final StringPath name = createString("name");

    public final EnumPath<com.kwon770.mm.RestaurantPrice> price = createEnum("price", com.kwon770.mm.RestaurantPrice.class);

    public final EnumPath<com.kwon770.mm.RestaurantType> type = createEnum("type", com.kwon770.mm.RestaurantType.class);

    public QRestaurant(String variable) {
        super(Restaurant.class, forVariable(variable));
    }

    public QRestaurant(Path<? extends Restaurant> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRestaurant(PathMetadata metadata) {
        super(Restaurant.class, metadata);
    }

}

