export const generateUniqueID = () => {
    return `id-${Date.now()}-${Math.floor(Math.random() * 10000)}`;
};