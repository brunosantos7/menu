import React, { useEffect, useState } from 'react';
import {
    View,
    ScrollView,
    Image,
    StyleSheet,
    FlatList,
    TouchableOpacity,
} from 'react-native';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import {
    faPhoneAlt,
    faMapMarkerAlt
} from '@fortawesome/free-solid-svg-icons';
import MnText from '../components/MnText';
import { colors } from '../constants';
import MenuService from '../services/MenuService';
import RestaurantService from '../services/RestaurantService';
import CategoryService from '../services/CategoryService';

const ProductItem = ({ product }) => {
    return (
        <TouchableOpacity style={styles.product} activeOpacity={0.6}>
            {product.imageUri && (
                <Image 
                    style={styles.productImage} 
                    source={{ uri: product.imageUri }} 
                />
            )}
            <View style={styles.productDetails}>
                <MnText style={styles.productName}>{product.name}</MnText>
                <MnText light>{product.description}</MnText>
                <MnText light style={styles.productPrice}>R$ 20,00</MnText>
            </View>
            
        </TouchableOpacity>
    );
};

const CategoryItem = ({ category, selected, onPressCategory }) => {
    function getBackgroundColor() {
        return {
            backgroundColor: selected ? colors.primary : colors.lightGray,
        };
    }

    function getTextStyle() {
        return {
            color: selected ? colors.white : colors.black,
        }
    }

    return (
        <TouchableOpacity 
            style={[styles.categoryItem, getBackgroundColor()]} 
            onPress={() => onPressCategory(category)}
        >
            <MnText style={getTextStyle()}>{category.name}</MnText>
        </TouchableOpacity>
    );
};

const Restaurant = ({ navigation }) => {
    const [restaurant, setRestaurant] = useState({});
    const [categories, setCategories] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState([]);
    const [products, setProducts] = useState([]);
    const [isLoadingProducts, setIsLoadingProducts] = useState(false);

    useEffect(() => {
        const restaurant = navigation.getParam('restaurant', {});
        setRestaurant(restaurant);
    }, []);

    useEffect(() => {
        if (restaurant && restaurant.id > 0) {
            getCategories();
        }
    }, [restaurant]);

    useEffect(() => {
        if (selectedCategory && selectedCategory.id > 0) {
            setProducts(selectedCategory.products);
        }
    }, [selectedCategory]);

    async function getCategories() {
        const categories = await RestaurantService.getProducts(
            restaurant.id,
        ).catch(error => {
            console.log(error);
        });
        
        setCategories(categories);

        if (categories.length > 0) {
            setSelectedCategory(categories[0]);
        }
    }

    function onSelectCategory(category) {
        setSelectedCategory(category);
    }

    return (
        <ScrollView style={styles.container}>
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

            {categories.length > 0 && (
                <View style={styles.categoryList}>
                    <FlatList
                        showsHorizontalScrollIndicator={false}
                        data={categories}
                        horizontal={true}
                        renderItem={({ item }) => {
                            let selected = false;

                            if (selectedCategory && selectedCategory.id == item.id) {
                                selected = true;
                            }

                            return <CategoryItem 
                                category={item} 
                                selected={selected}
                                onPressCategory={(category) => {
                                    onSelectCategory(category);
                                }} 
                            />
                        }}
                        keyExtractor={item => item.id.toString()}
                    />
                </View>
            )}

            <View style={styles.productList}>
                {(!isLoadingProducts && products.length == 0) && (
                    <View style={styles.noProducts}>
                        <MnText>Nenhum produto encontrado!</MnText>
                    </View>
                )}
                {products.length > 0 && products.map(product => (
                    <ProductItem product={product} key={product.id.toString()} />
                ))}
            </View>
        </ScrollView>
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
        resizeMode: 'cover',
    },
    details: {
        marginTop: 10,
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
    categoryList: {
        marginTop: 10,
        backgroundColor: colors.white,
    },
    categoryItem: {
        marginVertical: 10,
        marginHorizontal: 5,
        backgroundColor: colors.lightGray,
        paddingHorizontal: 20,
        paddingVertical: 8,
        borderRadius: 30,
    },
    noProducts: {
        paddingVertical: 20,
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: colors.white,
    },
    productList: {
        marginTop: 10,
    },
    product: {
        paddingHorizontal: 10,
        paddingVertical: 10,
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        backgroundColor: colors.white,
        marginBottom: 10,
    },
    productImage: {
        width: 50,
        height: 50,
    },
    productDetails: {
        flex: 1,
        marginLeft: 10,
    },
    productName: {
        fontSize: 16,
    },
    productPrice: {
        color: colors.primary,
        fontSize: 14,
    }
});

export default Restaurant;
