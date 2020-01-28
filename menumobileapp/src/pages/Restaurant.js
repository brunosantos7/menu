import React, { useEffect, useState } from 'react';
import {
    View,
    Image,
    StyleSheet,
    FlatList,
    TouchableOpacity,
} from 'react-native';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import {
    faUtensils,
    faPhoneAlt
} from '@fortawesome/free-solid-svg-icons';
import MnText from '../components/MnText';
import { colors } from '../constants';
import MenuService from '../services/MenuService';
import RestaurantService from '../services/RestaurantService';

const RestaurantHeader = ({ restaurant }) => {
    if (restaurant.uri) {
        return (
            <View style={styles.header}>
                <Image style={styles.image} source={{ uri: restaurant.uri }} />
                <View style={styles.details}>
                    <MnText bold style={styles.title}>{restaurant.name}</MnText>
                    <MnText style={styles.subtitle}>{restaurant.street + ', ' + restaurant.number}</MnText>
                    <MnText style={styles.subtitle}>{restaurant.neighborhood + ' - ' + restaurant.city + '/' + restaurant.state}</MnText>
                    <View style={styles.phone}>
                        <FontAwesomeIcon icon={faPhoneAlt} size={16} color={colors.white} />
                        <MnText bold style={styles.phoneText}>{restaurant.phone}</MnText>
                    </View>
                </View>
            </View>
        );
    }

    return <RestaurantHeaderNoImage restaurant={restaurant} />;
};

const RestaurantHeaderNoImage = ({ restaurant }) => {
    return <MnText>{restaurant.name}</MnText>;
};

const CategoryItemImage = ({ category }) => {
    if (category.uri) {

    }

    return (
        <FontAwesomeIcon
            icon={faUtensils}
            size={40}
            color={colors.white}
        />
    );
};

const CategoryItem = ({ category }) => {
    return (
        <TouchableOpacity style={styles.category}>
            <View style={styles.categoryIcon}>
                <CategoryItemImage category={category} />
            </View>
            <MnText bold style={styles.categoryName}>
                {category.name}
            </MnText>
        </TouchableOpacity>
    );
};

const Restaurant = ({ navigation }) => {
    const [restaurant, setRestaurant] = useState({});
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        const restaurant = navigation.getParam('restaurant', {});
        setRestaurant(restaurant);
    }, []);

    useEffect(() => {
        if (restaurant && restaurant.id > 0) {
            getCategories();
        }
    }, [restaurant]);

    async function getCategories() {
        const menus = await RestaurantService.getMenus(restaurant.id).catch(
            error => {
                console.log(error);
            },
        );

        if (menus.length > 0) {
            const categories = await MenuService.getCategories(
                menus[0].id,
            ).catch(error => {
                console.log(error);
            });
            setCategories(categories);
        }
    }

    return (
        <View style={styles.container}>
            <FlatList
                data={categories}
                renderItem={({ item }) => <CategoryItem category={item} />}
                keyExtractor={item => item.id.toString()}
                ListHeaderComponent={() => (
                    <RestaurantHeader restaurant={restaurant} />
                )}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        // marginVertical: 15,
        // marginHorizontal: 15,
    },
    header: {
        height: 190,
        backgroundColor: colors.white,
    },
    image: {
        height: 190,
        width: '100%',
        flex: 1,
        resizeMode: 'cover',
    },
    details: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        width: '100%',
        height: 190,
        position: 'absolute',
        backgroundColor: 'rgba(0, 0, 0, 0.6)'
    },
    title: {
        fontSize: 32,
        color: colors.white,
    },
    subtitle: {
        fontSize: 14,
        color: colors.white,
    },
    phone: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: colors.primary,
        borderRadius: 5,
        paddingHorizontal: 10,
        paddingVertical: 2,
        marginTop: 10,
    },
    phoneText: {
        fontSize: 16,
        color: colors.white,
        marginLeft: 10,
    },
    category: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        backgroundColor: colors.white,
        marginTop: 10,
        shadowColor: '#CCCCCC',
        shadowOffset: { width: 0, height: 1 },
        shadowOpacity: 0.5,
        shadowRadius: 1,
        borderWidth: 1,
        borderColor: colors.lightGray,
    },
    categoryIcon: {
        width: 70,
        height: 70,
        backgroundColor: colors.gray,
        alignItems: 'center',
        justifyContent: 'center',
    },
    categoryName: {
        fontSize: 16,
        color: colors.black,
        flex: 1,
        marginLeft: 10,
        marginRight: 10,
    },
});

export default Restaurant;
