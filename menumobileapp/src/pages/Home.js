import React, { useEffect, useState } from 'react';
import { FlatList, StyleSheet } from 'react-native';
import RestaurantTile from '../components/RestaurantTile';
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
        <FlatList
            contentContainerStyle={styles.container}
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
        />
    );
};

const styles = StyleSheet.create({
    container: {
        paddingVertical: 10,
    },
});

export default Home;
