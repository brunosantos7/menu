import React, { useState } from 'react';
import { View, TextInput, StyleSheet } from 'react-native';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
import { colors } from '../constants';

const InputSearch = ({ value, placeholder, onChange }) => {
    function onChangeText(text) {
        if (onChange) {
            onChange(text);
        }
    }

    return (
        <View style={styles.container}>
            <FontAwesomeIcon
                icon={faSearch}
                size={18}
                color={colors.gray}
                style={styles.icon}
            />
            <TextInput
                style={styles.input}
                onChangeText={text => onChangeText(text)}
                value={value}
                placeholder={placeholder}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        backgroundColor: colors.lightGray,
        borderRadius: 5,
    },
    icon: {
        marginHorizontal: 10,
    },
    input: {
        height: 40,
        fontFamily: 'Muli',
        flex: 1,
    },
});

export default InputSearch;
