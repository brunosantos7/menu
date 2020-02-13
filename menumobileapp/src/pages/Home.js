import React, { useEffect, useState } from 'react';
import AsyncStorage from '@react-native-community/async-storage';
import {
    FlatList,
    StyleSheet,
    SafeAreaView,
    View,
    TouchableOpacity,
    Dimensions
} from 'react-native';
import RestaurantTile from '../components/RestaurantTile';
import RestaurantCard from '../components/RestaurantCard';
import RestaurantCard1 from '../components/RestaurantCard1';
import MnText from '../components/MnText';
import RestaurantService from '../services/RestaurantService';
import { colors } from '../constants';
import InputSearch from '../components/InputSearch';

const {height, width} = Dimensions.get('window');

const Home = ({ navigation }) => {
    const [restaurants, setRestaurants] = useState([]);
    const [city, setCity] = useState(null);

    useEffect(() => {
        getCity();
    }, []);

    useEffect(() => {
        getRestaurants(null);
    }, [city]);

    function onPressRestaurant(restaurant) {
        navigation.navigate('Restaurant', {
            title: restaurant.name,
            restaurant: restaurant,
        });
    }

    async function getCity() {
        const city = await AsyncStorage.getItem('city');
        setCity(JSON.parse(city));
    }

    async function getRestaurants(searchText) {
        if (!city) {
            return;
        }

        const params = {
            city: city.city
        };

        if (searchText) {
            params.name = searchText;
        }

        const items = await RestaurantService.getRestaurants(params);
        setRestaurants(items);
    }

    function onSubmitSearch(searchText) {
        getRestaurants(searchText);
    }

    function onPressChangeCity() {
        navigation.navigate('CityList');
    }

    return (
        <SafeAreaView style={styles.container}>
            <FlatList
                style={{height: height - 20}}
                data={restaurants}
                renderItem={({ item }) => (
                    <RestaurantCard1
                        item={item}
                        onPressItem={() => {
                            onPressRestaurant(item);
                        }}
                    />
                )}
                keyExtractor={item => item.id.toString()}
                ListHeaderComponent={() => (
                    <View style={styles.header}>
                        <MnText bold style={styles.headerTitle}>Busca</MnText>
                        <View style={styles.headerSearch}>
                            <InputSearch 
                                placeholder="Busque por item ou restaurante" 
                                onSubmit={onSubmitSearch}
                            />
                        </View>

                        <MnText bold style={styles.headerTitle}>Restaurantes</MnText>
                        {city && <View style={styles.headerCity}>
                            <MnText style={styles.headerSubTitle}>
                                em {city.city}/{city.state}
                            </MnText>
                            <TouchableOpacity onPress={onPressChangeCity}>
                                <MnText style={styles.headerButton}>
                                    (Trocar cidade)
                                </MnText>
                            </TouchableOpacity>
                        </View>}

                        {!city && <View style={styles.headerCity}>
                            <TouchableOpacity onPress={onPressChangeCity}>
                                <MnText style={styles.headerButton}>
                                    Escolher Cidade
                                </MnText>
                            </TouchableOpacity>
                        </View>}
                    </View>
                )}
            />
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        paddingVertical: 10,
        backgroundColor: colors.background,
    },
    header: {
        marginHorizontal: 15,
        marginVertical: 15,
    },
    headerTitle: {
        fontSize: 24,
    },
    headerSubTitle: {
        fontSize: 12,
        color: colors.primary,
    },
    headerCity: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
    },
    headerSearch: {
        marginTop: 10,
        marginBottom: 15,
        flex: 1,
    },
    headerButton: {
        fontSize: 12,
        color: colors.blue,
    },
});

export default Home;
