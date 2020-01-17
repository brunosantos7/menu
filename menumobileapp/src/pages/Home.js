import React, { useEffect, useState } from 'react';
import { FlatList, StyleSheet, SafeAreaView, View, TouchableOpacity } from 'react-native';
import RestaurantTile from '../components/RestaurantTile';
import MnText from '../components/MnText';
import image1 from '../../assets/images/image1.jpg';
import image2 from '../../assets/images/image2.jpg';
import image3 from '../../assets/images/image3.jpg';
import image4 from '../../assets/images/image4.jpg';
import image5 from '../../assets/images/image5.jpg';
import image6 from '../../assets/images/image6.jpg';
import image7 from '../../assets/images/image7.jpg';
import image8 from '../../assets/images/image8.jpg';
import image9 from '../../assets/images/image9.jpg';
import image10 from '../../assets/images/image10.jpg';
import { colors } from '../constants';
import InputSearch from '../components/InputSearch';

const Home = ({ navigation }) => {
    const [restaurants, setRestaurants] = useState([]);

    useEffect(() => {
        const items = [
            {
                id: 1,
                name: 'Deró Lanches',
                category: 'Lanchonete',
                image: image1,
            },
            {
                id: 2,
                name: 'Sal e Brasa',
                category: 'Bar e Restaurante',
                image: image2,
            },
            {
                id: 3,
                name: 'Bikão Lanches III',
                category: 'Lanchonete',
                image: image3,
            },
            {
                id: 4,
                name: 'Cabrito',
                category: 'Restaurante',
                image: image4,
            },
            {
                id: 5,
                name: 'Caipirão',
                category: 'Restaurante',
                image: image5,
            },
            {
                id: 6,
                name: 'Carreiro',
                category: 'Bar e Restaurante',
                image: image6,
            },
            {
                id: 7,
                name: 'Calabria',
                category: 'Pizzaria e Restaurante',
                image: image7,
            },
            {
                id: 8,
                name: 'Panela de Ferro',
                category: 'Restaurante',
                image: image8,
            },
            {
                id: 9,
                name: 'Avenida Beers',
                category: 'Bar e Restaurante',
                image: image9,
            },
            {
                id: 10,
                name: 'Açai.com',
                category: 'Lanchonete e Açai',
                image: image10,
            }
        ];
        setRestaurants(items);
    }, []);

    function onPressRestaurant(restaurant) {
        navigation.navigate('CategoryList', {
            restaurant: restaurant,
        });
    }

    return (
        <SafeAreaView style={styles.container}>
            <FlatList
                // contentContainerStyle={styles.container}
                data={restaurants}
                renderItem={({ item }) => (
                    <RestaurantTile
                        item={item}
                        onPressItem={() => {
                            onPressRestaurant(item);
                        }}
                    />
                )}
                keyExtractor={item => item.id.toString()}
                ListHeaderComponent={() => (
                    <View style={styles.header}>
                        <MnText style={styles.headerTitle}>Busca</MnText>
                        <View style={styles.headerSearch}>
                            <InputSearch placeholder="Busque por restaurante ou menu" />
                        </View>

                        <MnText style={styles.headerTitle}>Restaurantes</MnText>
                        <View style={styles.headerCity}>
                            <MnText style={styles.headerSubTitle}>em Araguari/MG</MnText>
                            <TouchableOpacity>
                                <MnText style={styles.headerButton}>(Trocar cidade)</MnText>
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
        backgroundColor: colors.lightGray
    },
    header: {
        marginHorizontal: 15,
        marginVertical: 15,
    },
    headerTitle: {
        fontSize: 18,
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
        color: colors.blue
    }
});

export default Home;
