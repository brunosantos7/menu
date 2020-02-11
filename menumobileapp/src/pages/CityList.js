import React, { useEffect, useState } from 'react';
import { SectionList, Button, StyleSheet, View, TouchableOpacity } from 'react-native';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faCheck } from '@fortawesome/free-solid-svg-icons';
import MnText from '../components/MnText';
import { colors } from '../constants';
import AsyncStorage from '@react-native-community/async-storage';

const CityList = ({ navigation }) => {
    const [datasource, setDatasource] = useState([]);
    const [city, setCity] = useState(null);

    useEffect(() => {
        async function init() {
            try {
                let cities = await AsyncStorage.getItem('citiesList');
                cities = JSON.parse(cities);
                const datasource = [];

                for (const c in cities) {
                    datasource.push({
                        title: c,
                        data: cities[c],
                    })
                }

                setDatasource(datasource);
            } catch (e) {
                console.log(e);
            }
        }

        init();
    }, []);

    function onSelectCity(city) {
        setCity(city);
        navigation.setParams({city: city});
    }

    return (
        <View style={styles.container}>
            <SectionList
                sections={datasource}
                keyExtractor={(item, index) => item + index}
                renderItem={({ item }) => (
                    <TouchableOpacity style={styles.listItem} activeOpacity={0.8} onPress={() => onSelectCity(item)}>
                        <MnText style={styles.listItemText}>{item.city}</MnText>
                        {(city && item.city == city.city) && <FontAwesomeIcon icon={faCheck} />}
                    </TouchableOpacity>
                )}
                renderSectionHeader={({ section: { title } }) => (
                    <View style={styles.listHeader}>
                        <MnText bold style={styles.listHeaderText}>{title}</MnText>
                    </View>
                )}
            />
        </View>
    );
};

CityList.navigationOptions = ({ navigation }) => ({
    title: 'Escolha a Cidade',
    headerLeft: () => <></>,
    headerRight: () => <Button onPress={() => {
        const city = navigation.getParam('city', null);
        
        if (city) {
            AsyncStorage.setItem('city', JSON.stringify(city)).then(() => {
                navigation.navigate('App');
            });
        } else {
            console.log('Escolha a cidade!!!');
        }
    }} title="OK" />
});

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: colors.lightGray,
    },
    listHeader: {
        paddingHorizontal: 10,
        paddingVertical: 5,
        backgroundColor: colors.lightGray,
    },
    listHeaderText: {
        fontSize: 14,
    },
    listItem: {
        padding: 10,
        backgroundColor: colors.white,
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
    },
    listItemText: {
        fontSize: 16,
    }
})

export default CityList;