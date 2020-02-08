import React, { useEffect } from 'react';
import { View, StyleSheet } from 'react-native';
import MnText from '../components/MnText';
import { colors } from '../constants';
import AsyncStorage from '@react-native-community/async-storage';
import RestaurantService from '../services/RestaurantService';

const LoadingPage = ({ navigation }) => {
    useEffect(() => {
        setTimeout(() => {
            init();
        }, 200);
    }, []);

    async function init() {
        try {
            const city = await AsyncStorage.getItem('city');

            if (city !== null) {
                navigation.navigate('App');
            } else {
                const cities = await RestaurantService.getCitiesWithStates();
                await AsyncStorage.setItem('citiesList', JSON.stringify(cities));
                navigation.navigate('CityList');
            }
        } catch(e) {
            console.log(e);
        }
    }
    
    return (
        <View style={styles.container}>
            <MnText bold style={styles.loadingText}>Loading...</MnText>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: colors.primary,
    },
    loadingText: {
        fontSize: 21,
        color: colors.white,
    }
});

export default LoadingPage;
