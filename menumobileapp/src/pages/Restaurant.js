import React, { useEffect, useState } from 'react';
import {
    View,
    Image,
    StyleSheet,
    FlatList,
    TouchableOpacity,
    Dimensions,
} from 'react-native';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import {
    faUtensils,
    faPhoneAlt,
    faMapMarkerAlt
} from '@fortawesome/free-solid-svg-icons';
import MnText from '../components/MnText';
import { colors } from '../constants';
import MenuService from '../services/MenuService';
import RestaurantService from '../services/RestaurantService';

const tileWidth = (Math.round(Dimensions.get('window').width) - 45) / 2;

const RestaurantHeader = ({ restaurant }) => {
    return (
        <View style={styles.header}>
            {restaurant.uri && <Image style={styles.image} source={{ uri: restaurant.uri }} />}
            <View style={styles.details}>
                <MnText bold style={styles.title}>Sobre</MnText>
                <View style={styles.detailRow}>
                    <FontAwesomeIcon icon={faMapMarkerAlt} size={20} color={colors.black} />
                    <View style={{marginLeft: 10}}>
                        <MnText style={styles.subtitle}>{restaurant.street + ', ' + restaurant.number}</MnText>
                        <MnText style={styles.subtitle}>{restaurant.neighborhood + ' - ' + restaurant.city + '/' + restaurant.state}</MnText>
                    </View>
                </View>
                <View style={styles.detailRow}>
                    <FontAwesomeIcon icon={faPhoneAlt} size={20} color={colors.black} />
                    <View style={{marginLeft: 10}}>
                        <MnText style={styles.subtitle}>{restaurant.phone}</MnText>
                    </View>
                </View>
            </View>
        </View>
    );
};

const CategoryItemImage = ({ category }) => {
    if (category.imageUri) {
        return (
            <Image 
                style={styles.categoryImage} 
                source={{ uri: category.imageUri }} 
            />
        );
    }

    return (
        <FontAwesomeIcon
            icon={faUtensils}
            size={80}
            color={colors.white}
        />
    );
};

const CategoryItem = ({ category }) => {
    return (
        <TouchableOpacity style={styles.category} activeOpacity={0.6}>
            <View style={styles.categoryIcon}>
                <CategoryItemImage category={category} />
            </View>
            <View style={styles.categoryMask}>
                <MnText light style={styles.categoryName}>
                    {category.name}
                </MnText>
            </View>
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
                numColumns={2}
                renderItem={({ item }) => <CategoryItem category={item} />}
                keyExtractor={item => item.id.toString()}
                columnWrapperStyle={styles.listColumnWrapper}
                ListHeaderComponent={() => (
                    <RestaurantHeader restaurant={restaurant} />
                )}
                ListHeaderComponentStyle={styles.listHeaderStyle}
                // ListFooterComponent={() => (
                //     <View style={{height: 15, backgroundColor: colors.white}} />
                // )}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    header: {
        backgroundColor: colors.lightGray,
    },
    image: {
        height: 220,
        width: '100%',
        flex: 1,
        resizeMode: 'cover',
    },
    details: {
        marginTop: 10,
        flex: 1,
        backgroundColor: colors.white,
        paddingHorizontal: 10,
        paddingVertical: 15,
    },
    detailRow: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'flex-start',
        marginTop: 10,
    },
    title: {
        fontSize: 16,
        color: colors.black,
    },
    subtitle: {
        fontSize: 14,
        color: colors.black,
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
    listHeaderStyle: {
        marginBottom: 10,
    },
    listColumnWrapper: {
        justifyContent: 'space-between',
        backgroundColor: colors.white,
        paddingHorizontal: 15,
        paddingTop: 15,
    },
    category: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        width: tileWidth,
    },
    categoryMask: {
        width: tileWidth,
        height: tileWidth,
        position: 'absolute',
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        alignItems: 'center',
        justifyContent: 'center',
    },
    categoryImage: {
        width: tileWidth,
        height: tileWidth,
    },
    categoryIcon: {
        width: tileWidth,
        height: tileWidth,
        backgroundColor: colors.lightGray,
        alignItems: 'center',
        justifyContent: 'center',
    },
    categoryName: {
        fontSize: 22,
        color: colors.white,
    },
});

export default Restaurant;
