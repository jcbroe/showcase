const Discord = require('discord.js');
const client = new Discord.Client();
const config = require('./config.json');
const Enmap = require("enmap");
client.points = new Enmap({name: "points"});


client.once('ready', () => {
	console.log(`Bot has started, with ${client.users.size} users, in ${client.channels.size} channels of ${client.guilds.size} guilds.`);
});

client.on('message', async message => {
	if(message.author.bot) return;

	if (message.guild) {
		const key = `${message.guild.id}-${message.author.id}`;

		client.points.ensure(`${message.guild.id}-${message.author.id}`, {
			user: message.author.id,
			guild: message.guild.id,
			points: 0,
			level: 1
		});

		client.points.inc(key, "points");

		const curLevel = Math.floor(0.1 * Math.sqrt(client.points.get(key, "points")));

		if (client.points.get(key, "level") < curLevel) {
			message.reply(`You've leveled up to level **${curlevel}**!`);
			client.points.set(key, curLevel, "level");
		}
	}

	if(!message.content.startsWith(config.prefix)) return;

    const args = message.content.slice(config.prefix.length).trim().split(/ +/g);
  	const command = args.shift().toLowerCase();


	if (command === 'ping') {
		const m = await message.channel.send("Ping?");
    	m.edit(`Pong! Latency is ${m.createdTimestamp - message.createdTimestamp}ms. API Latency is ${Math.round(client.ping)}ms`);
	}

	if (command === 'say') {
		const sayMessage = args.join(" ");
		message.delete().catch(O_o=>{});
		message.channel.send(sayMessage);
	}

	if (command === "points") {
		const key = `${message.guild.id}-${message.author.id}`;
		return message.channel.send(`You currently have ${client.points.get(key, "points")} points, and are level ${client.points.get(key, "level")}!`);
	}

	if (command === "leaderboard") {
		const filtered = client.points.filter(p => p.guild === message.guild.id).array();
		const sorted = filtered.sort((a, b) => b.points - a.points);
		const top10 = sorted.splice(0, 10);
		const embed = new Discord.RichEmbed()
			.setTitle("Leaderboard")
			.setAuthor(client.user.username, client.user.avatarURL)
			.setDescription("Top 10 Point Leaders!")
			.setColor(0x00AE86);
		for(const data of top10) {
			embed.addFielf(client.users.get(data.user).tag, `${data.points} points (level ${data.level})`);
		}
		return message.channel.send({embed});
	}

});

client.login(config.token);
