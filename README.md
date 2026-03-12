# 🎣 AdvancedFishing

AdvancedFishing is a Minecraft plugin that expands the vanilla fishing system. With this plugin, players can catch **unique fish**, with special **traits**, **different sizes**, and a **progression system**. The goal is to make fishing more engaging and rewarding.

---

## Features

- **Custom Fish**: Unique fish species that players can collect  
- **Traits**: Special properties like `Shiny`, `Ancient`, etc.  
- **Sizes**: Fish can have different sizes (Tiny, Huge)  
- **Configurable**: Easy to extend the fish system  
- **Developer Friendly**: Clean code structure for adding new features  
- **Command `/givefish`** to give custom fish  

---

## Installation

1. Download the latest plugin version  
2. Place the `.jar` file in your server’s `plugins` folder  
3. Start or restart your server  
4. Configuration files will be generated automatically  

---

## Commands

### `/givefish`

Gives a custom fish to a player.


/givefish [fish]

/givefish [fish] [trait]

/givefish [fish] [trait] [size]

/givefish [player] [fish]

/givefish [player] [fish] [trait]

/givefish [player] [fish] [trait] [size]


---

## Permissions

| Permission | Description |
|-----------|-------------|
| `advancedfishing.commands.givefish.self` | Allows giving fish to yourself |
| `advancedfishing.commands.givefish.others` | Allows giving fish to other players |

---

## Fish System

Each fish consists of:

- **Fish Type** – the fish
- **Rarity** - rarity of the fish
- **Trait** – special property (optional)  
- **Size** – special property (optional)
- **Weight** - random weight (min to max from the fish)

**Examples:**

```Rare Tiny Salmon [3,42kg]```



Developed by Yannik
