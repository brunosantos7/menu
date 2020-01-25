import React, { useEffect, useState } from 'react';
import {
    FlatList,
    StyleSheet,
    SafeAreaView,
    View,
    TouchableOpacity,
} from 'react-native';
import RestaurantTile from '../components/RestaurantTile';
import RestaurantCard from '../components/RestaurantCard';
import RestaurantCard1 from '../components/RestaurantCard1';
import MnText from '../components/MnText';
import RestaurantService from '../services/RestaurantService';
import { colors } from '../constants';
import InputSearch from '../components/InputSearch';

const Home = ({ navigation }) => {
    const [restaurants, setRestaurants] = useState([]);

    useEffect(() => {
        async function init() {
            const items = await RestaurantService.getRestaurants();
            setRestaurants(items);
        }

        init();
    }, []);

    function onPressRestaurant(restaurant) {
        navigation.navigate('CategoryList', {
            restaurant: restaurant,
        });
    }

    return (
        <SafeAreaView style={styles.container}>
            <FlatList
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
                            <InputSearch placeholder="Busque por item ou restaurante" />
                        </View>

                        <MnText bold style={styles.headerTitle}>Restaurantes</MnText>
                        <View style={styles.headerCity}>
                            <MnText style={styles.headerSubTitle}>
                                em Araguari/MG
                            </MnText>
                            <TouchableOpacity>
                                <MnText style={styles.headerButton}>
                                    (Trocar cidade)
                                </MnText>
                            </TouchableOpacity>
                        </View>
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
