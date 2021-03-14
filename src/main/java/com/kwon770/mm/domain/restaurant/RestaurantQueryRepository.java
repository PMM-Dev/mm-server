package com.kwon770.mm.domain.restaurant;

import com.kwon770.mm.RestaurantLocation;
import com.kwon770.mm.RestaurantPrice;
import com.kwon770.mm.RestaurantType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.kwon770.mm.domain.restaurant.QRestaurant.restaurant;

@Repository
public class RestaurantQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public RestaurantQueryRepository(JPAQueryFactory queryFactory) {
        super(Restaurant.class);
        this.queryFactory = queryFactory;
    }

    @Transactional
    public List<Restaurant> findAllByConditions(String type, String price, String location, String deliveryable) {
        return queryFactory
                .selectFrom(restaurant)
                .where(
                    eqType(type),
                        eqPrice(price),
                        eqLocation(location),
                        eqDevliveryable(deliveryable)
                )
                .fetch();
    }

    private BooleanExpression eqType(String _type) {
        if (StringUtils.isEmpty(_type)) {
            return null;
        }
        return restaurant.type.eq(RestaurantType.valueOf(_type));
    }
    private BooleanExpression eqPrice(String _price) {
        if (StringUtils.isEmpty(_price)) {
            return null;
        }
        return restaurant.price.eq(RestaurantPrice.valueOf(_price));
    }
    private BooleanExpression eqLocation(String _locaiton) {
        if (StringUtils.isEmpty(_locaiton)) {
            return null;
        }
        return restaurant.location.eq(RestaurantLocation.valueOf(_locaiton));
    }
    private BooleanExpression eqDevliveryable(String _deliveryable) {
        if (StringUtils.isEmpty(_deliveryable)) {
            return null;
        }
        return restaurant.deliveryable.eq(_deliveryable.equals("true") ? true : false);
    }
}
